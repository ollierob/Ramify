import {Place} from "../../protobuf/generated/place_pb";

export function addPlaceHistory(place: Place.AsObject): PlaceHistory {
    let currentHistory = getPlaceHistory();
    if (!place) return currentHistory;
    if (currentHistory.length && currentHistory[0].id == place.id) return currentHistory;
    const placeWithoutParent: Place.AsObject = place.parent ? {...place, parent: null} : place;
    const history = [placeWithoutParent].concat(currentHistory);
    savePlaceHistory(history);
    return history;
}

function savePlaceHistory(history: PlaceHistory) {
    if (history.length > MAX_HISTORY) history = history.slice(0, MAX_HISTORY);
    sessionStorage.setItem("place-history", JSON.stringify(history));
}

export function getPlaceHistory(): PlaceHistory {
    return JSON.parse(sessionStorage.getItem("place-history")) || [];
}

const MAX_HISTORY = 10;
export type PlaceHistory = Place.AsObject[];