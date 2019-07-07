import {Place} from "../../protobuf/generated/place_pb";
import {placeTypeName} from "./PlaceType";

export type PlaceId = string;

export function placeHref(place: Place.AsObject) {
    const type = placeTypeName(place.type).toLowerCase();
    return placePrefix() + "#/" + type + "?place=" + place.id;
}

function placePrefix() {
    try {
        const current = window.location.pathname;
        if (current && !current.startsWith("/places")) return "/places";
    } catch (e) {
    }
    return "";
}

export type PlaceList = ReadonlyArray<Place.AsObject>