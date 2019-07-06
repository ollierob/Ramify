import * as React from "react";
import {DateRange} from "../../../components/date/DateRange";
import {Card} from "antd";
import {Institution} from "../../../protobuf/generated/institution_pb";
import {HasClass} from "../../../components/style/HasClass";
import {PlaceDescription} from "../../../protobuf/generated/place_pb";
import {RecordCards} from "./InstitutionRecordCards";

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