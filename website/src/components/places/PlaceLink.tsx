import * as React from "react";
import {Place} from "../../protobuf/generated/place_pb";
import {placeHref} from "../../pages/places/PlaceLinks";
import {placeTypeName} from "./PlaceType";
import {LoadingIcon} from "../images/Icons";

export const PlaceLink = (props: {place: Place.AsObject, showType?: boolean, loading?: boolean}) => {

    if (props.loading) return <span className="place">
        <LoadingIcon/>
    </span>;

    if (!props.place) return null;

    return <span className="place">
        <a href={placeHref(props.place)}>
            {props.place.name}
            {props.showType && <> {placeTypeName(props.place.type)}</>}
        </a>
    </span>;

};