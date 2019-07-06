import * as React from "react";
import {InstitutionRecordSet} from "../../../protobuf/generated/institution_pb";
import {Place} from "../../../protobuf/generated/place_pb";
import {Card} from "antd";
import {DateRange} from "../../../components/date/DateRange";
import {placeHref} from "../../../components/places/Place";
import {placeTypeName} from "../../../components/places/PlaceType";

export const RecordCards = (props: {records: ReadonlyArray<InstitutionRecordSet.AsObject>, alsoSee: ReadonlyArray<Place.AsObject>}) => {
    const records = props.records;
    if (!records || !records.length) return null;
    return <div className="institutionRecords">
        {records.map(record => <RecordCard record={record}/>)}
        {props.alsoSee && <AlsoSeeCard alsoSee={props.alsoSee}/>}
    </div>
};

const RecordCard = (props: {record: InstitutionRecordSet.AsObject}) => {
    const record = props.record;
    return <Card title={record.name} className="placeCard">
        Available <DateRange date={record.covers}/>
        {record.notes && <div className="notes">{record.notes}</div>}
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