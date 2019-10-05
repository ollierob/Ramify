import {PlaceId} from "./Place";
import {protoDelete, protoGet, protoStringPost} from "../fetch/ProtoFetch";
import {Place, PlaceList as PlaceListProto} from "../../protobuf/generated/place_pb";

export interface PlaceFavouritesLoader {

    get(): Promise<ReadonlyArray<Place.AsObject>>

    add(id: PlaceId): Promise<ReadonlyArray<Place.AsObject>>

    remove(id: PlaceId): Promise<ReadonlyArray<Place.AsObject>>

}

class ProtoPlaceFavouritesLoader implements PlaceFavouritesLoader {

    get() {
        return protoGet("/places/favourites/all", PlaceListProto.deserializeBinary).then(readPlaces);
    }

    add(id: PlaceId) {
        return protoStringPost("/places/favourites/add", id, PlaceListProto.deserializeBinary).then(readPlaces);
    }

    remove(id: PlaceId) {
        return protoDelete("/places/favourites/remove/" + id, PlaceListProto.deserializeBinary).then(readPlaces);
    }

}

function readPlaces(l: PlaceListProto): Place.AsObject[] {
    return l ? l.getPlaceList().map(p => p.toObject()) : [];
}

export const DEFAULT_PLACE_FAVOURITES_LOADER: PlaceFavouritesLoader = new ProtoPlaceFavouritesLoader();