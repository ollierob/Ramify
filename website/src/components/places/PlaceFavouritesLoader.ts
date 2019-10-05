import {PlaceId} from "./Place";
import {protoDelete, protoGet, protoPost} from "../fetch/ProtoFetch";
import {PlaceIdList} from "../../protobuf/generated/place_pb";

export interface PlaceFavouritesLoader {

    get(): Promise<ReadonlyArray<PlaceId>>

    add(id: PlaceId): Promise<ReadonlyArray<PlaceId>>

    remove(id: PlaceId): Promise<ReadonlyArray<PlaceId>>

}

class ProtoPlaceFavouritesLoader implements PlaceFavouritesLoader {

    get(): Promise<ReadonlyArray<PlaceId>> {
        return protoGet("/places/favourites/all", PlaceIdList.deserializeBinary).then(readIds);
    }

    add(id: PlaceId): Promise<ReadonlyArray<PlaceId>> {
        return protoPost("/places/favourites/add", id, (p, w) => w.writeString(p), PlaceIdList.deserializeBinary).then(readIds);
    }

    remove(id: PlaceId): Promise<ReadonlyArray<PlaceId>> {
        return protoDelete("/places/favourites/remove/" + id, PlaceIdList.deserializeBinary).then(readIds);
    }

}

function readIds(l: PlaceIdList): PlaceId[] {
    return l ? l.getIdList() : [];
}

export const DEFAULT_PLACE_FAVOURITES_LOADER: PlaceFavouritesLoader = new ProtoPlaceFavouritesLoader();