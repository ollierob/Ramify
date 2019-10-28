import {Place} from "../../protobuf/generated/place_pb";
import {placeTypeName} from "../../components/places/PlaceType";

export function placeHref(place: Place.AsObject) {
    return placePrefix() + "#/group?place=" + place.id;
}

function placePrefix() {
    try {
        const current = window.location.pathname;
        if (current && !current.startsWith("/places")) return "/places";
    } catch (e) {
    }
    return "";
}