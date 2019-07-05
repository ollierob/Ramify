import * as React from "react";
import {DateRange} from "../../../components/date/DateRange";
import {Card} from "antd";
import {Institution, InstitutionRecordSet} from "../../../protobuf/generated/institution_pb";
import {HasClass} from "../../../components/style/HasClass";
import {PlaceDescription} from "../../../protobuf/generated/place_pb";

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

        <RecordCards records={institution.recordsetList}/>

    </Card>

};

const Title = (props: {institution: Institution.AsObject}) => {
    const institution = props.institution;
    return <>
        <b>{institution.place.name}</b>
        {institution.type && <> <span className="gray">{institution.type}</span></>}
    </>
};

const RecordCards = (props: {records: ReadonlyArray<InstitutionRecordSet.AsObject>}) => {
    const records = props.records;
    if (!records || !records.length) return null;
    return <div className="institutionRecords">
        {records.map(record => <RecordCard record={record}/>)}
    </div>
}

const RecordCard = (props: {record: InstitutionRecordSet.AsObject}) => {
    const record = props.record;
    return <Card title={record.name} className="placeCard">
        Available <DateRange date={record.covers}/>
    </Card>
};
