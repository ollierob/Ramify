import * as React from "react";
import {Place} from "../../protobuf/generated/place_pb";
import {Card} from "antd";
import {placeTypeName} from "../places/PlaceType";
import {RecordSet} from "../../protobuf/generated/record_pb";
import {FormattedYearRange} from "../date/FormattedDateRange";
import {placeHref} from "../../pages/places/PlaceLinks";
import {recordSetHref} from "../../pages/records/RecordLinks";
import "./RecordCards.css";

export const RecordCards = (props: {records: ReadonlyArray<RecordSet.AsObject>, alsoSee?: ReadonlyArray<Place.AsObject>}) => {
    const records = props.records;
    if (!records || !records.length) return null;
    return <div className="recordCards">
        {records.map(record => <RecordCard record={record}/>)}
        {props.alsoSee && <AlsoSeeCard alsoSee={props.alsoSee}/>}
    </div>;
};

const RecordCard = (props: {record: RecordSet.AsObject}) => {
    const record = props.record;
    return <Card
        title={<a href={recordSetHref(record)}>{record.title}</a>}
        className="recordCard">
        Available <FormattedYearRange date={record.date}/>
        {record.description && <div className="notags">{record.description}</div>}
    </Card>;
};


const AlsoSeeCard = (props: {alsoSee: ReadonlyArray<Place.AsObject>}) => {
    const alsoSee = props.alsoSee;
    if (!alsoSee || !alsoSee.length) return null;
    return <Card title="Also see" className="recordCard">
        <ul>
            {alsoSee.map(place => <li>
                <a href={placeHref(place)}>{place.name}</a>
                {" "}
                ({placeTypeName(place.type)})
            </li>)}
        </ul>
    </Card>;
};