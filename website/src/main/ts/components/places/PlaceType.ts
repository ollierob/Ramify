//TODO this should be determined server-side

import {PlaceType as PlaceTypeProtoValues, PlaceTypeMap} from "../../protobuf/generated/place_pb";
import {numberMap} from "../Maps";

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

const ValueToPlaceLookup = numberMap(Object.keys(PlaceTypeProtoValues), k => PlaceTypeProtoValues[k]);

export function placeTypeName(type: PlaceTypeMap[keyof PlaceTypeMap], plural: boolean = false): string {
    const n = PlaceTypeNames[ValueToPlaceLookup[type]];
    if (!n) return null;
    return plural ? (n.p || n.s + "s") : n.s;
}

export function sortByType(t1: PlaceType, t2: PlaceType): number {
    const v1 = PlaceTypeProtoValues[t1];
    const v2 = PlaceTypeProtoValues[t2];
    return v1 - v2;
}
