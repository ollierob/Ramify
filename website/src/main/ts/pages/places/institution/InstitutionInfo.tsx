import * as React from "react";
import {DateRange} from "../../../components/date/DateRange";
import {Card} from "antd";
import {Institution, InstitutionRecordSet} from "../../../protobuf/generated/institution_pb";
import {HasClass} from "../../../components/style/HasClass";
import {Place, PlaceDescription} from "../../../protobuf/generated/place_pb";
import {placeHref} from "../../../components/places/Place";

type Props = HasClass & {
    description: PlaceDescription.AsObject;
    institution: Institution.AsObject;
    children?: React.ReactNode
};

export const InstitutionInfo = (props: Props) => {

    const institution = props.institution;
    if (!institution) return null;

    const description = props.description;

    return <Card
        className={"info " + (props.className || "")}
        style={props.style}
        title={<Title institution={institution}/>}>

        <div className="description">
            {institution.established && <div>Founded <DateRange date={institution.established}/></div>}
            {institution.closed && <div>Closed <DateRange date={institution.closed}/></div>}
            {description && <div>{description.description}</div>}
        </div>

        {props.children}

        <RecordCards
            records={institution.recordsetList}
            alsoSee={description && description.alsoseeList}/>

    </Card>

};

const Title = (props: {institution: Institution.AsObject}) => {
    const institution = props.institution;
    return <>
        <b>{institution.place.name}</b>
        {institution.type && <> <span className="gray">{institution.type}</span></>}
    </>
};

const RecordCards = (props: {records: ReadonlyArray<InstitutionRecordSet.AsObject>, alsoSee: ReadonlyArray<Place.AsObject>}) => {
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
    </Card>
};


const AlsoSeeCard = (props: {alsoSee: ReadonlyArray<Place.AsObject>}) => {
    const alsoSee = props.alsoSee;
    if (!alsoSee || !alsoSee.length) return null;
    return <Card title="Also see" className="placeCard">
        <ul>
            {alsoSee.map(p => <li><a href={placeHref(p)}>{p.name}</a></li>)}
        </ul>
    </Card>
};