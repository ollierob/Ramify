import * as React from "react";
import {Place, PlaceDescription} from "../../protobuf/generated/place_pb";
import {HasClass} from "../style/HasClass";
import {Card} from "antd";
import {PlaceContextMenu} from "./PlaceContextMenu";
import {Loading} from "../style/Loading";

type Props = HasClass & {
    loading: boolean;
    place: Place.AsObject;
    description: PlaceDescription.AsObject;
}

export const PlaceInfo = (props: Props) => {

    if (props.loading) return <Loading/>;
    if (!props.place || !props.description) return null;

    return <Card
        className={"placeInfo " + (props.className || "")}
        style={props.style}
        title={<PlaceContextMenu place={props.place}/>}>

        {props.description.description || <i>No description available.</i>}

    </Card>;

};