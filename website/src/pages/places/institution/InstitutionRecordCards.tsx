import * as React from "react";
import {Place} from "../../../protobuf/generated/place_pb";
import {Card} from "antd";
import {FormattedDateRange} from "../../../components/date/FormattedDateRange";
import {placeHref} from "../../../components/places/Place";
import {placeTypeName} from "../../../components/places/PlaceType";
import {RecordSet} from "../../../protobuf/generated/record_pb";

export const RecordCards = (props: {records: ReadonlyArray<RecordSet.AsObject>, alsoSee: ReadonlyArray<Place.AsObject>}) => {
    const records = props.records;
    if (!records || !records.length) return null;
    return <div className="institutionRecords">
        {records.map(record => <RecordCard record={record}/>)}
        {props.alsoSee && <AlsoSeeCard alsoSee={props.alsoSee}/>}
    </div>
};

const RecordCard = (props: {record: RecordSet.AsObject}) => {
    const record = props.record;
    return <Card title={record.title} className="placeCard">
        Available <FormattedDateRange date={record.date}/>
        {record.description && <div className="notes">{record.description}</div>}
    </Card>
};


const AlsoSeeCard = (props: {alsoSee: ReadonlyArray<Place.AsObject>}) => {
    const alsoSee = props.alsoSee;
    if (!alsoSee || !alsoSee.length) return null;
    return <Card title="Also see" className="placeCard">
        <ul>
            {alsoSee.map(place => <li>
                <a href={placeHref(place)}>{place.name}</a>
                {" "}
                ({placeTypeName(place.type)})
            </li>)}
        </ul>
    </Card>
};