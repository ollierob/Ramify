import {PlaceBundle, PlaceGroup} from "../../protobuf/generated/place_pb";
import {sortPlacesByDateAndName} from "./Place";

export type PlaceGroupId = string;

export type ResolvedPlaceGroup = {
    group: PlaceGroup.AsObject
    children: ReadonlyArray<PlaceBundle.AsObject>
    iso?: string
}

export function synthesizePlaceGroup(place: PlaceBundle.AsObject): ResolvedPlaceGroup {
    if (!place) return null;
    return {
        group: {id: place.place.groupid, name: place.place.name, defaultchild: place.place, otherchildrenList: []},
        children: [place],
        iso: place.place.iso
    };
}

export function sortPlaceGroupChildren(group: PlaceGroup.AsObject, children: ReadonlyArray<PlaceBundle.AsObject>): ReadonlyArray<PlaceBundle.AsObject> {
    if (!children || !children.length) return [];
    return [...children].sort((p1, p2) => {
        if (group.defaultchild.id == p1.place.id) return -1;
        if (group.defaultchild.id == p2.place.id) return +1;
        return sortPlacesByDateAndName(p1.place, p2.place);
    });
}