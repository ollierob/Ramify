import {Place} from "../../protobuf/generated/place_pb";
import {PlaceId, PlaceList} from "./Place";
import {addNewPlace} from "./PlaceHistory";

export function addPlaceFavourite(place: Place.AsObject): PlaceList {
    let previous = getPlaceFavourites();
    if (!place) return previous;
    const favourites = addNewPlace(place, previous);
    savePlaceFavourites(favourites);
    return favourites;
}

export function removePlaceFavourite(id: PlaceId): PlaceList {
    const current = getPlaceFavourites();
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

export function getPlaceFavourites(): PlaceList {
    //TODO persist server-side
    return JSON.parse(sessionStorage.getItem("place-favourites")) || [];
}

export function isPlaceFavourite(id: PlaceId): boolean {
    return getPlaceFavourites().some(s => s.id == id);
}