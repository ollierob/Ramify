import * as React from "react";
import {DateRange} from "../../../components/date/DateRange";
import {Card} from "antd";
import {Institution} from "../../../protobuf/generated/institution_pb";
import {HasClass} from "../../../components/style/HasClass";

export const InstitutionInfo = (props: {institution: Institution.AsObject} & HasClass) => {

    const institution = props.institution;
    if (!institution) return null;

    return <Card
        className={"info " + (props.className || "")}
        style={props.style}
        title={<Title institution={institution}/>}>

        {institution.established && <div>Founded <DateRange date={institution.established}/></div>}
        {institution.closed && <div>Closed <DateRange date={institution.closed}/></div>}

    </Card>

};

const Title = (props: {institution: Institution.AsObject}) => {
    const institution = props.institution;
    return <>
        <b>{institution.place.name}</b>
        {institution.type && <> <span className="gray">{institution.type}</span></>}
    </>
}