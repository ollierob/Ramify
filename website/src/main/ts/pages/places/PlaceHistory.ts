import {Place} from "../../protobuf/generated/place_pb";
import {PlaceList} from "../../components/places/Place";

export function addPlaceHistory(place: Place.AsObject): PlaceList {
    let currentHistory = getPlaceHistory();
    if (!place) return currentHistory;
    if (currentHistory.length && currentHistory[0].id == place.id) return currentHistory;
    {
        const currentIndex = currentHistory.findIndex(h => h.id == place.id);
        if (currentIndex >= 0) currentHistory.splice(currentIndex, 1);
    }
    const placeWithoutParent: Place.AsObject = place.parent ? {...place, parent: null} : place;
    const history = [placeWithoutParent].concat(currentHistory);
    savePlaceHistory(history);
    return history;
}

function savePlaceHistory(history: PlaceList): void {
    if (history.length > MAX_HISTORY) history = history.slice(0, MAX_HISTORY);
    sessionStorage.setItem("place-history", JSON.stringify(history));
}

export function getPlaceHistory(): PlaceHistory {
    return JSON.parse(sessionStorage.getItem("place-history")) || [];
}

const MAX_HISTORY = 10;
type PlaceHistory = Place.AsObject[];