import * as React from "react";
import {Place, PlaceDescription} from "../../protobuf/generated/place_pb";
import {placeTypeName} from "./PlaceType";
import {PlaceLinks} from "./PlaceLinks";
import {addPlaceFavourite, isPlaceFavourite, removePlaceFavourite} from "./PlaceFavourites";
import {FavouritesIcon} from "../Icons";

export const PlaceTitle = (props: {place: Place.AsObject, description: PlaceDescription.AsObject;}) => {
    const place = props.place;
    if (!place) return null;
    const isFavourite = isPlaceFavourite(place.id);
    return <>
        <b>{place.name}</b>
        {" "}
        <span className="unimportant">
            {placeTypeName(place.type)}
        </span>
        {" "}
        <FavouritesIcon
            className={isFavourite && "favourite"}
            onClick={() => isFavourite ? removePlaceFavourite(place.id) : addPlaceFavourite(place)}/>
        <PlaceLinks
            links={props.description && props.description.linkList}/>
    </>
};