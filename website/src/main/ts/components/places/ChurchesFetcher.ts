import {PlaceId} from "./Place";
import {Church} from "../../protobuf/generated/church_pb";
import {protoFetch} from "../fetch/ProtoFetch";
import {PlaceIdList} from "../../protobuf/generated/place_pb";

export interface ChurchesFetcher {

    fetchChurches(region: PlaceId): Promise<ReadonlyArray<PlaceId>>

    fetchChurch(id: PlaceId): Promise<Church.AsObject>

}

class ProtoChurchesFetcher implements ChurchesFetcher {

    fetchChurches(region: PlaceId) {
        return protoFetch("/places/churches/in/" + region, PlaceIdList.deserializeBinary)
            .then(l => l ? l.getIdList() : []);
    }

    fetchChurch(id: PlaceId) {
        return protoFetch("/places/churches/at/" + id, Church.deserializeBinary)
            .then(c => c ? c.toObject() : null);
    }

}

export const DefaultChurchFetcher: ChurchesFetcher = new ProtoChurchesFetcher();