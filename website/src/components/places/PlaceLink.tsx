import * as React from "react";
import {Place} from "../../protobuf/generated/place_pb";
import {placeHref} from "../../pages/places/PlaceLinks";
import {LoadingIcon} from "../images/Icons";

export const PlaceLink = (props: {place: Place.AsObject, showType?: boolean, loading?: boolean}) => {

    if (props.loading) return <span className="place">
        <LoadingIcon/>
    </span>;

    const place = props.place;
    if (!place) return null;

    //Prefix suffix
    const prefix = props.showType && place.type.canprefix && !place.type.cansuffix && <>{place.type.name} of </>;
    const suffix = props.showType && place.type.cansuffix && !place.name.endsWith(place.type.name) && <> {place.type.name}</>;

    return <span className="place">
        {prefix}
        <a href={placeHref(place)}>{place.name}</a>
        {suffix}
    </span>;

};