import * as React from "react";
import {Place, PlaceDescription} from "../../protobuf/generated/place_pb";
import {placeTypeName} from "./PlaceType";
import {PlaceLinks} from "./PlaceLinks";
import {isPlaceFavourite, PlaceFavouritesHandler} from "./PlaceFavourites";
import {FavouritesIcon} from "../images/Icons";

export const PlaceTitle = (props: {place: Place.AsObject, description: PlaceDescription.AsObject} & PlaceFavouritesHandler) => {

    const place = props.place;
    if (!place) return null;

    const isFavourite = isPlaceFavourite(place.id, props.placeFavourites);

    return <>
        <b>{place.name}</b>
        {" "}
        <span className="unimportant">
            {placeTypeName(place.type)}
        </span>
        {" "}
        <FavouritesIcon
            className={isFavourite && "favourite"}
            onClick={() => isFavourite ? props.removePlaceFavourite(place) : props.addPlaceFavourite(place)}/>
        <PlaceLinks
            links={props.description && props.description.linkList}/>
    </>

};