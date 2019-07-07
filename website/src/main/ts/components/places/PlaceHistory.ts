import {Place} from "../../protobuf/generated/place_pb";
import {PlaceList} from "./Place";

export function addPlaceHistory(place: Place.AsObject): PlaceList {
    let currentHistory = getPlaceHistory();
    if (!place) return currentHistory;
    const history = addNewPlace(place, currentHistory);
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

export function addNewPlace(place: Place.AsObject, list: PlaceList, permitDuplicates: boolean = false): PlaceList {
    place = place.parent ? {...place, parent: null} : place;
    if (!list.length) return [place];
    if (list[0].id == place.id) return list;
    if (!permitDuplicates) {
        const i = list.findIndex(h => h.id == place.id);
        if (i >= 0) return [].concat(list).splice(i, 1).concat(place);
    }
    return [place].concat(list);
}

export type PlaceHistoryHandler = {
    placeHistory: PlaceList;
    addPlaceHistory: (place: Place.AsObject) => void;
}