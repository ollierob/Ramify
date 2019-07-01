import {PlaceId} from "./Place";
import {Church} from "../../protobuf/generated/church_pb";
import {protoFetch} from "../fetch/ProtoFetch";
import {PlaceIdList} from "../../protobuf/generated/place_pb";

export interface ChurchLoader {

    loadChurches(region: PlaceId): Promise<ReadonlyArray<PlaceId>>

    loadChurch(id: PlaceId): Promise<Church.AsObject>

}

class ProtoChurchLoader implements ChurchLoader {

    loadChurches(region: PlaceId) {
        return protoFetch("/places/churches/in/" + region, PlaceIdList.deserializeBinary)
            .then(l => l ? l.getIdList() : []);
    }

    loadChurch(id: PlaceId) {
        return protoFetch("/places/churches/at/" + id, Church.deserializeBinary)
            .then(c => c ? c.toObject() : null);
    }

}

export const DEFAULT_CHURCH_LOADER: ChurchLoader = new ProtoChurchLoader();

