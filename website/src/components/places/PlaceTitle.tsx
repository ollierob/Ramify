import * as React from "react";
import {Place, PlaceDescription} from "../../protobuf/generated/place_pb";
import {placeTypeName} from "./PlaceType";
import {LinkTags} from "../style/Links";
import {isPlaceFavourite, PlaceFavouritesHandler} from "./PlaceFavourites";
import {FavouritesIcon} from "../images/Icons";
import {Flag} from "../images/Flag";

export const PlaceTitle = (props: {place: Place.AsObject, description: PlaceDescription.AsObject} & PlaceFavouritesHandler) => {

    const place = props.place;
    if (!place) return null;

    const isFavourite = isPlaceFavourite(place.id, props.placeFavourites());

    return <>

        <Flag iso={place.iso}/>
        <b>{place.name}</b>

        {" "}

        <span className="unimportant">
            {placeTypeName(place.type)}
        </span>

        {" "}

        <FavouritesIcon
            className={isFavourite && "favourite"}
            onClick={() => isFavourite ? props.removePlaceFavourite(place) : props.addPlaceFavourite(place)}/>

        <LinkTags
            links={props.description && props.description.linkList}/>

    </>;

};