import {Place} from "../../protobuf/generated/place_pb";
import {PlaceId, PlaceList} from "./Place";
import {addNewPlace} from "./PlaceHistory";

export function addSessionPlaceFavourite(place: Place.AsObject): PlaceList {
    let previous = getSessionPlaceFavourites();
    if (!place) return previous;
    const favourites = addNewPlace(place, previous);
    savePlaceFavourites(favourites);
    return favourites;
}

export function removeSessionPlaceFavourite(id: PlaceId | Place.AsObject): PlaceList {

    const current = getSessionPlaceFavourites();
    if (!id) return current;
    if (typeof id != "string") id = id.id;

    const i = current.findIndex(c => c.id == id);
    if (i >= 0) {
        const updated: Place.AsObject[] = [].concat(current);
        updated.splice(i, 1);
        savePlaceFavourites(updated);
        return updated;
    }

    return current;

}

function savePlaceFavourites(list: PlaceList) {
    sessionStorage.setItem("place-favourites", JSON.stringify(list));
}

export function getSessionPlaceFavourites(): PlaceList {
    //TODO persist server-side
    return JSON.parse(sessionStorage.getItem("place-favourites")) || [];
}

export function isPlaceFavourite(id: PlaceId, favourites: PlaceList): boolean {
    return favourites && favourites.some(s => s.id == id);
}

export type PlaceFavouritesHandler = {
    placeFavourites: () => PlaceList;
    addPlaceFavourite: (place: Place.AsObject) => void;
    removePlaceFavourite: (place: Place.AsObject) => void;
}

export const SessionPlaceFavouritesHandler: PlaceFavouritesHandler = {

    addPlaceFavourite: addSessionPlaceFavourite,

    removePlaceFavourite: removeSessionPlaceFavourite,

    placeFavourites: getSessionPlaceFavourites

};