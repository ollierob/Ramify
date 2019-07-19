import * as React from "react";
import {Place} from "../../protobuf/generated/place_pb";
import {placeHref} from "../../pages/places/PlaceLinks";
import {placeTypeName} from "./PlaceType";

export const PlaceContextMenu = (props: {place: Place.AsObject}) => {

    if (!props.place) return null;

    return <span className="place">
        <a href={placeHref(props.place)}>{props.place.name}</a>
        {" "}
        <span className="type unimportant">{placeTypeName(props.place.type)}</span>
    </span>;

};