//TODO this should be determined server-side

import {PlaceType as PlaceTypeProtoValues, PlaceTypeMap} from "../../protobuf/generated/place_pb";
import {numberMap, numericKeys} from "../Maps";

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
    PARISH: {s: "Parish", p: "Parishes"},
    RAPE: {s: "Rape"},
    SCHOOL: {s: "School"},
    STATE: {s: "State"},
    TOWN: {s: "Town"},
    TOWNSHIP: {s: "Township"},
    VILLAGE: {s: "Village"},
    WAPENTAKE: {s: "Wapentake"},
};

const ValueToPlaceLookup = numberMap(numericKeys(PlaceTypeProtoValues), v => PlaceTypeProtoValues[v]);

export function placeTypeName(type: PlaceTypeMap[keyof PlaceTypeMap], plural: boolean = false) {
    const n = PlaceTypeNames[ValueToPlaceLookup[type]];
    return plural ? (n.p || n.s + "s") : n.s;
}

export function sortByType(t1: PlaceType, t2: PlaceType): number {
    const v1 = PlaceTypeProtoValues[t1];
    const v2 = PlaceTypeProtoValues[t2];
    return v1 - v2;
}
