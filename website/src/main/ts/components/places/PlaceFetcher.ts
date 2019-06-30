import {PlaceId} from "./Place";
import {Place} from "../../protobuf/generated/place_pb";
import {protoFetch} from "../fetch/ProtoFetch";

export interface PlaceFetcher {

    fetchPlace(id: PlaceId): Promise<Place.AsObject>

}

class ProtoPlaceFetcher implements PlaceFetcher {

    fetchPlace(id: PlaceId) {
        return protoFetch("/places/at/" + id, Place.deserializeBinary)
            .then(p => p ? p.toObject() : null);
    }

}

export const DEFAULT_PLACE_FETCHER: PlaceFetcher = new ProtoPlaceFetcher();