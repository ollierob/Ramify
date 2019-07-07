import {Place} from "../../protobuf/generated/place_pb";

export function addPlaceHistory(place: Place.AsObject): PlaceHistory {
    if (!place) return getPlaceHistory();
    const placeWithoutParent: Place.AsObject = place.parent ? {...place, parent: null} : place;
    const history = getPlaceHistory().concat(placeWithoutParent);
    savePlaceHistory(history)
    return history;
}

function savePlaceHistory(history: PlaceHistory) {
    if (history.length >= MAX_HISTORY) history = history.slice(0, MAX_HISTORY);
    sessionStorage.setItem("place-history", JSON.stringify(history));
}

export function getPlaceHistory(): PlaceHistory {
    return JSON.parse(sessionStorage.getItem("place-history")) || [];
}

const MAX_HISTORY = 10;
type PlaceHistory = Place.AsObject[];