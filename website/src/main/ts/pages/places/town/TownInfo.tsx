import * as React from "react";
import {Card} from "antd";
import {Place} from "../../../protobuf/generated/place_pb";

export const TownInfo = (props: {place: Place.AsObject}) => {

    const place = props.place;
    if (!place) return null;

    return <Card
        title={<><b>{place.name}</b></>}>

    </Card>

}