import {Place} from "../../protobuf/generated/place_pb";
import {PlaceList} from "../../components/places/Place";
import {addNewPlace} from "./PlaceHistory";

export function addPlaceFavourite(place: Place.AsObject) {
    if (!place) return;
    const favourites = addNewPlace(place, getPlaceFavourites());
    sessionStorage.setItem("place-favourites", JSON.stringify(favourites));
}

export function getPlaceFavourites(): PlaceList {
    //TODO persist server-side
    return JSON.parse(sessionStorage.getItem("place-favourites")) || [];
}