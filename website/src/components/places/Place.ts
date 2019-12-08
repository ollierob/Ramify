import {Place} from "../../protobuf/generated/place_pb";
import {earliestYear, latestYear} from "../date/DateRange";

export type PlaceId = string;
export type PlaceList = ReadonlyArray<Place.AsObject>

export function sortPlacesByDateAndName(p1: Place.AsObject, p2: Place.AsObject): number {
    return sortPlacesByDate(p1, p2) || sortPlacesByName(p1, p2);
}

function sortPlacesByDate(p1: Place.AsObject, p2: Place.AsObject) {
    if (!p1.history) return p2.history ? -1 : 0;
    if (!p2.history) return +1;
    let comp = (earliestYear(p1.history.open) || 0) - (earliestYear(p2.history.open) || 0);
    if (comp != 0) return comp;
    return (latestYear(p1.history.close) || 9999) - (latestYear(p2.history.close) || 9999);
}

export function sortPlacesByType(p1: Place.AsObject, p2: Place.AsObject) {
    return p1.type.name.localeCompare(p2.type.name);
}

export function sortPlacesByName(p1: Place.AsObject, p2: Place.AsObject) {
    return p1.name.localeCompare(p2.name);
}