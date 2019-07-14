import * as React from "react";
import {Place} from "../../protobuf/generated/place_pb";
import {placeHref} from "../../pages/places/PlaceLinks";

export const PlaceContextMenu = (props: {place: Place.AsObject}) => {

    if (!props.place) return null;

    return <>
        <a href={placeHref(props.place)}>{props.place.name}</a>
    </>;

};