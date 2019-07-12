import {Place} from "../../protobuf/generated/place_pb";
import {placeTypeName} from "./PlaceType";

export type PlaceId = string;
export type PlaceList = ReadonlyArray<Place.AsObject>