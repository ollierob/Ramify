//TODO this should be determined server-side

import {Place, PlaceType as PlaceTypeProtoValues, PlaceTypeMap} from "../../protobuf/generated/place_pb";
import {NumberMap, numberMap} from "../Maps";

export type PlaceType = keyof PlaceTypeMap;
type PlaceInfo = {s: string, p?: string, d?: string}

const PlaceTypeInfo: { [key in PlaceType]: PlaceInfo } = {
    BOROUGH: {s: "Borough"},
    CHAPELRY: {s: "Chapelry", p: "Chapelries"},
    CITY: {s: "City", p: "Cities"},
    CIVIL_PARISH: {s: "Civil Parish", p: "Civil Parishes"},
    COUNTRY: {s: "Country", p: "Countries"},
    COUNTY: {s: "County", p: "Counties"},
    COURT: {s: "Court"},
    CHURCH: {s: "Church", p: "Churches"},
    DISTRICT: {s: "District"},
    FARMSTEAD: {s: "Farmstead"},
    GRAVEYARD: {s: "Graveyard"},
    GRAVESHIP: {s: "Graveship"},
    HAMLET: {s: "Hamlet"},
    HOSPITAL: {s: "Hospital"},
    HOUSE: {s: "House"},
    HUNDRED: {s: "Hundred"},
    INN: {s: "Inn"},
    LATHE: {s: "Lathe"},
    MANOR: {s: "Manor"},
    MILL: {s: "Mill"},
    MONASTERY: {s: "Monastery", p: "Monasteries"},
    NEIGHBOURHOOD: {s: "Neighbourhood"},
    PARISH: {s: "Church Parish", p: "Church Parishes"},
    RAPE: {s: "Rape"},
    RIDING: {s: "Riding"},
    RURAL_DISTRICT: {s: "Rural District"},
    SCHOOL: {s: "School"},
    STATE: {s: "State"},
    STREET: {s: "Street"},
    TOWN: {s: "Town"},
    TOWNSHIP: {s: "Township"},
    URBAN_DISTRICT: {s: "Urban District"},
    VILLAGE: {s: "Village"},
    WAPENTAKE: {s: "Wapentake"},
};

function placeInfo(type: PlaceTypeMap[keyof PlaceTypeMap] | PlaceType): PlaceInfo {
    return PlaceTypeInfo[typeof type == "number" ? placeTypeKey(type) : type];
}

const PlaceTypes: ReadonlyArray<PlaceType> = Object.keys(PlaceTypeProtoValues) as Array<PlaceType>;
const ValueToPlaceTypeLookup: NumberMap<PlaceType> = numberMap(PlaceTypes, k => PlaceTypeProtoValues[k]);

export function placeTypeName(type: PlaceTypeMap[keyof PlaceTypeMap] | PlaceType, plural: boolean = false): string {
    const info = placeInfo(type);
    if (!info) return null;
    return plural ? (info.p || info.s + "s") : info.s;
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
