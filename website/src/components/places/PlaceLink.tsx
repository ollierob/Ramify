import * as React from "react";
import {Place} from "../../protobuf/generated/place_pb";
import {placeHref} from "../../pages/places/PlaceLinks";
import {placeTypeName} from "./PlaceType";
import {LoadingIcon} from "../images/Icons";

export const PlaceLink = (props: {place: Place.AsObject, showType?: boolean, loading?: boolean}) => {

    if (props.loading) return <span className="place">
        <LoadingIcon/>
    </span>;

    const place = props.place;
    if (!place) return null;

    const type = props.showType && placeTypeName(place.type);

    return <span className="place">
        <a href={placeHref(place)}>
            {place.name}
            {props.showType && !place.name.endsWith(type) && <> {type}</>}
        </a>
    </span>;

};