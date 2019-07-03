import {Place} from "../../protobuf/generated/place_pb";
import {placeTypeName} from "./PlaceType";

export type PlaceId = string;

export function placeHref(place: Place.AsObject) {
    const type = placeTypeName(place.type).toLowerCase();
    return "#/" + type + "?place=" + place.id;
}