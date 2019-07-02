import {PlaceId} from "./Place";
import {Place, PlaceList} from "../../protobuf/generated/place_pb";
import {protoFetch} from "../fetch/ProtoFetch";
import {queryParameters} from "../fetch/Fetch";

export interface PlaceLoader {

    loadPlace(id: PlaceId): Promise<Place.AsObject>

    loadChildren(id: PlaceId, maxDepth?: number): Promise<ReadonlyArray<Place.AsObject>>

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

}

export const DEFAULT_PLACE_LOADER: PlaceLoader = new ProtoPlaceLoader();