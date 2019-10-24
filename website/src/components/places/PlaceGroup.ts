import {PlaceBundle, PlaceGroup} from "../../protobuf/generated/place_pb";

export type PlaceGroupId = string;

export type ResolvedPlaceGroup = {
    group: PlaceGroup.AsObject
    children: ReadonlyArray<PlaceBundle.AsObject>
}