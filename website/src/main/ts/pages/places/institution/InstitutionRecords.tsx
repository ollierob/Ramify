import * as React from "react";
import {Card} from "antd";
import {Institution} from "../../../protobuf/generated/institution_pb";

export const InstitutionRecords = (props: {institution: Institution.AsObject}) => {

    const institution = props.institution;
    if (!institution) return null;

    return <Card
        className="churchRecords"
        title={"Records"}>

    </Card>

}