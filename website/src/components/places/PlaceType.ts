//TODO this should be determined server-side

import {Place, PlaceType as PlaceTypeProtoValues, PlaceTypeMap} from "../../protobuf/generated/place_pb";
import {NumberMap, numberMap} from "../Maps";

export type PlaceType = keyof PlaceTypeMap;
type PlaceInfo = {s: string, p?: string, d?: string, isBuilding?: boolean}

const PlaceTypeInfo: { [key in PlaceType]: PlaceInfo } = {
    BOROUGH: {s: "Borough"},
    CHAPELRY: {s: "Chapelry", p: "Chapelries"},
    CITY: {s: "City", p: "Cities"},
    CIVIL_PARISH: {s: "Civil Parish", p: "Civil Parishes"},
    COUNTRY: {s: "Country", p: "Countries"},
    COUNTY: {s: "County", p: "Counties"},
    CHURCH: {s: "Church", p: "Churches", isBuilding: true},
    DISTRICT: {s: "District"},
    FARMSTEAD: {s: "Farmstead", isBuilding: true},
    GRAVEYARD: {s: "Graveyard", isBuilding: true},
    GRAVESHIP: {s: "Graveship"},
    HAMLET: {s: "Hamlet"},
    HOSPITAL: {s: "Hospital", isBuilding: true},
    HOUSE: {s: "House", isBuilding: true},
    HUNDRED: {s: "Hundred"},
    INN: {s: "Inn", isBuilding: true},
    MANOR: {s: "Manor"},
    MILL: {s: "Mill", isBuilding: true},
    MONASTERY: {s: "Monastery", p: "Monasteries", isBuilding: true},
    NEIGHBOURHOOD: {s: "Neighbourhood"},
    PARISH: {s: "Church Parish", p: "Church Parishes"},
    RAPE: {s: "Rape"},
    SCHOOL: {s: "School", isBuilding: true},
    STATE: {s: "State"},
    STREET: {s: "Street", isBuilding: true},
    TOWN: {s: "Town", isBuilding: true},
    TOWNSHIP: {s: "Township"},
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

export function isBuilding(p1: Place.AsObject): boolean {
    return placeInfo(p1.type).isBuilding;
}
