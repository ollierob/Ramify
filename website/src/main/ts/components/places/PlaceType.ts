//TODO this should be determined server-side

import {Place, PlaceType as PlaceTypeProtoValues, PlaceTypeMap} from "../../protobuf/generated/place_pb";
import {NumberMap, numberMap} from "../Maps";

export type PlaceType = keyof PlaceTypeMap;
type Name = {s: string, p?: string}

const PlaceTypeNames: { [key in PlaceType]: Name } = {
    BOROUGH: {s: "Borough"},
    CITY: {s: "City"},
    COUNTRY: {s: "Country", p: "Countries"},
    COUNTY: {s: "County", p: "Counties"},
    CHURCH: {s: "Church", p: "Churches"},
    DISTRICT: {s: "District"},
    FARMSTEAD: {s: "Farmstead"},
    GRAVESHIP: {s: "Graveship"},
    HOSPITAL: {s: "Hospital"},
    HOUSE: {s: "House"},
    HUNDRED: {s: "Hundred"},
    MANOR: {s: "Manor"},
    PARISH: {s: "Parish", p: "Parishes"},
    RAPE: {s: "Rape"},
    SCHOOL: {s: "School"},
    STATE: {s: "State"},
    TOWN: {s: "Town"},
    TOWNSHIP: {s: "Township"},
    VILLAGE: {s: "Village"},
    WAPENTAKE: {s: "Wapentake"},
};

const PlaceTypes: ReadonlyArray<PlaceType> = Object.keys(PlaceTypeProtoValues) as Array<PlaceType>;
const ValueToPlaceTypeLookup: NumberMap<PlaceType> = numberMap(PlaceTypes, k => PlaceTypeProtoValues[k]);

export function placeTypeName(type: PlaceTypeMap[keyof PlaceTypeMap] | PlaceType, plural: boolean = false): string {
    const n = PlaceTypeNames[typeof type == "number" ? placeTypeKey(type) : type];
    if (!n) return null;
    return plural ? (n.p || n.s + "s") : n.s;
}

export function placeTypeKey(type: PlaceTypeMap[keyof PlaceTypeMap]): PlaceType {
    return ValueToPlaceTypeLookup[type];
}

export function sortByPlaceType(t1: PlaceType, t2: PlaceType): number {
    const v1 = PlaceTypeProtoValues[t1];
    const v2 = PlaceTypeProtoValues[t2];
    return v1 - v2;
}

export function sortByPlaceName(p1: Place.AsObject, p2: Place.AsObject): number {
    return p1.name.localeCompare(p2.name);
}
