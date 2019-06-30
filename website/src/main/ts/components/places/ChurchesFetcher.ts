import {PlaceId} from "./Place";
import {Church} from "../../protobuf/generated/church_pb";
import {protoFetch} from "../fetch/ProtoFetch";

export interface ChurchesFetcher {

    fetchChurches(region: PlaceId): Promise<ReadonlyArray<PlaceId>>

    fetchChurch(id: PlaceId): Promise<Church>

}

class ProtoChurchesFetcher implements ChurchesFetcher {

    fetchChurches(region: PlaceId) {
        return undefined;
    }

    fetchChurch(id: PlaceId) {
        return protoFetch("/places/churches/at/" + id, Church.deserializeBinary);
    }

}

export const DefaultChurchFetcher: ChurchesFetcher = new ProtoChurchesFetcher();