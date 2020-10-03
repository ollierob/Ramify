import {Place} from "../../protobuf/generated/place_pb";
import {PlaceList} from "./Place";

function pushPlaceHistory(place: Place.AsObject): PlaceList {
    let currentHistory = getSessionPlaceHistory();
    if (!place) return currentHistory;
    const history = addNewPlace(place, currentHistory);
    savePlaceHistory(history);
    return history;
}

function savePlaceHistory(history: PlaceList): void {
    if (history.length > MAX_HISTORY) history = history.slice(0, MAX_HISTORY);
    sessionStorage.setItem("place-history", JSON.stringify(history));
}

function getSessionPlaceHistory(): PlaceHistory {
    return JSON.parse(sessionStorage.getItem("place-history")) || [];
}

const MAX_HISTORY = 10;
type PlaceHistory = Place.AsObject[];

export function addNewPlace(place: Place.AsObject, list: PlaceList, permitDuplicates: boolean = false): PlaceList {
    //place = place.parent ? {...place, parent: null} : place;
    if (!list.length) return [place];
    if (list[0].id == place.id) return list;
    const newList = [place].concat(list);
    if (!permitDuplicates) {
        const i = newList.findIndex((h, i) => i > 0 && h.id == place.id);
        if (i >= 0) newList.splice(i, 1);
    }
    return newList;
}

export type PlaceHistoryHandler = {
    placeHistory: () => PlaceList;
    addPlaceHistory: (place: Place.AsObject) => void;
}

export const SessionPlaceHistoryHandler: PlaceHistoryHandler = {
    addPlaceHistory: pushPlaceHistory,
    placeHistory: getSessionPlaceHistory
};