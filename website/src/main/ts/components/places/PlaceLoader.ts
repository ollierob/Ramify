import {PlaceId} from "./Place";
import {Place, PlaceList} from "../../protobuf/generated/place_pb";
import {protoFetch} from "../fetch/ProtoFetch";
import {queryParameters} from "../fetch/Fetch";
import {Position} from "../../protobuf/generated/location_pb";

export interface PlaceLoader {

    loadPlace(id: PlaceId): Promise<Place.AsObject>

    loadChildren(id: PlaceId, maxDepth?: number): Promise<ReadonlyArray<Place.AsObject>>

    loadPosition(id: PlaceId): Promise<Position.AsObject>

}

class ProtoPlaceLoader implements PlaceLoader {

    loadPlace(id: PlaceId) {
        return protoFetch("/places/at/" + id, Place.deserializeBinary)
            .then(p => p ? p.toObject() : null);
    }

    loadChildren(id: string, maxDepth: number = 1) {
        return protoFetch("/places/children/" + id + queryParameters({depth: maxDepth}), PlaceList.deserializeBinary)
            .then(l => l ? l.getPlaceList().map(p => p.toObject()) : []);
    }

    loadPosition(id: string) {
        return protoFetch("/places/position/" + id, Position.deserializeBinary)
            .then(p => p ? p.toObject() : null);
    }


}

export const DEFAULT_PLACE_LOADER: PlaceLoader = new ProtoPlaceLoader();