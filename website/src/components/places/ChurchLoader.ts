import {PlaceId} from "./Place";
import {protoGet} from "../fetch/ProtoFetch";
import {PlaceIdList} from "../../protobuf/generated/place_pb";
import {Institution} from "../../protobuf/generated/institution_pb";

export interface ChurchLoader {

    loadChurches(region: PlaceId): Promise<ReadonlyArray<PlaceId>>

    loadChurch(id: PlaceId): Promise<Institution.AsObject>

}

class ProtoChurchLoader implements ChurchLoader {

    loadChurches(region: PlaceId) {
        return protoGet("/places/churches/in/" + region, PlaceIdList.deserializeBinary)
            .then(l => l ? l.getIdList() : []);
    }

    loadChurch(id: PlaceId) {
        return protoGet("/places/churches/at/" + id, Institution.deserializeBinary)
            .then(c => c ? c.toObject() : null);
    }

}

export const DEFAULT_CHURCH_LOADER: ChurchLoader = new ProtoChurchLoader();

