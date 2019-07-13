import {RecordSet, RecordSetList} from "../../protobuf/generated/record_pb";
import {PlaceId} from "../places/Place";
import {queryParameters} from "../fetch/Fetch";
import {protoFetch} from "../fetch/ProtoFetch";
import {RecordSetId, sortRecordSetByTitle} from "./RecordSet";

export interface RecordLoader {

    loadRecordSet(id: RecordSetId): Promise<Readonly<RecordSet.AsObject>>

    loadRecordSets(options?: RecordSetOptions): Promise<ReadonlyArray<RecordSet.AsObject>>;

    loadChildRecordSets(id: RecordSetId): Promise<ReadonlyArray<RecordSet.AsObject>>

}

type RecordSetOptions = {place?: PlaceId, limit?: number}

class ProtoRecordLoader implements RecordLoader {

    loadRecordSet(id: string) {
        return protoFetch("/records/set/" + id, RecordSet.deserializeBinary)
            .then(s => s ? s.toObject() : null);
    }

    loadRecordSets(options) {
        const url = "/records/sets" + queryParameters(options);
        return protoFetch(url, RecordSetList.deserializeBinary).then(readRecordSets);
    }

    loadChildRecordSets(id: string): Promise<ReadonlyArray<RecordSet.AsObject>> {
        return protoFetch("/records/sets/children/" + id, RecordSetList.deserializeBinary).then(readRecordSets);
    }

}

function readRecordSets(list: RecordSetList): RecordSet.AsObject[] {
    if (!list) return [];
    return list.getRecordsetList().map(l => l.toObject()).sort(sortRecordSetByTitle);
}

export const DEFAULT_RECORD_LOADER: RecordLoader = new ProtoRecordLoader();