import * as React from "react";
import {Church} from "../../../protobuf/generated/church_pb";
import {DateRange} from "../../../components/date/DateRange";
import {Card} from "antd";

export const ChurchInfo = (props: {church: Church.AsObject}) => {

    const church = props.church;
    if (!church) return null;

    return <Card
        className="churchInfo"
        title={<Title church={church}/>}>

        {church.established && <div>Founded <DateRange date={church.established}/></div>}
        {church.closed && <div>Closed <DateRange date={church.closed}/></div>}

    </Card>

};

const Title = (props: {church: Church.AsObject}) => {
    const church = props.church;
    return <>
        <b>{church.place.name}</b>
        {" "}
        <span className="gray">{church.denomination}</span>
    </>
}