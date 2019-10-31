import * as React from "react";
import {Place, PlaceDescription} from "../../../protobuf/generated/place_pb";
import {HasClass} from "../../../components/style/HasClass";
import {Card} from "antd";
import {PlaceLink} from "../../../components/places/PlaceLink";
import {Loading} from "../../../components/style/Loading";

type Props = HasClass & {
    loading: boolean;
    place: Place.AsObject;
    description: PlaceDescription.AsObject;
    preTitle?: React.ReactNode;
}

export const RecordPlaceInfo = (props: Props) => {

    if (props.loading) return <Loading/>;
    if (!props.place) return null;

    return <Card
        className={"placeInfo " + (props.className || "")}
        style={props.style}
        title={<>{props.preTitle}<PlaceLink place={props.place} showType/></>}>

        {props.description && props.description.description || <i>No description available.</i>}

    </Card>;

};