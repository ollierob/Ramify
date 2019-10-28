import {PlaceBundle, PlaceGroup} from "../../protobuf/generated/place_pb";

export type PlaceGroupId = string;

export type ResolvedPlaceGroup = {
    group: PlaceGroup.AsObject
    children: ReadonlyArray<PlaceBundle.AsObject>
}

export function synthesizePlaceGroup(place: PlaceBundle.AsObject): ResolvedPlaceGroup {
    if (!place) return null;
    const id = place.place.id;
    return {
        group: {id: id, name: place.place.name, defaultchildid: id, otherchildidList: []},
        children: [place]
    };
}