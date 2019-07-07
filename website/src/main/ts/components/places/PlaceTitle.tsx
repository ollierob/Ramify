import * as React from "react";
import {Place, PlaceDescription} from "../../protobuf/generated/place_pb";
import {placeTypeName} from "./PlaceType";
import {PlaceLinks} from "./PlaceLinks";

export const PlaceTitle = (props: {place: Place.AsObject, description: PlaceDescription.AsObject;}) => {
    const place = props.place;
    return <>
        <b>{place.name}</b>
        {" "}
        <span className="unimportant">{placeTypeName(place.type)}</span>
        <PlaceLinks links={props.description && props.description.linkList}/>
    </>
};