import {Place} from "../../protobuf/generated/place_pb";

export type PlaceId = string;

export function placeHref(place: Place.AsObject) {
    return "#/" + place.type.toLowerCase() + "?place=" + place.id;
}