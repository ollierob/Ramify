import * as React from "react";
import {Church} from "../../../protobuf/generated/church_pb";
import {Card} from "antd";

export const ChurchRecords = (props: {church: Church.AsObject}) => {

    const church = props.church;
    if (!church) return null;

    return <Card
        className="churchRecords"
        title={"Records"}>

    </Card>

}