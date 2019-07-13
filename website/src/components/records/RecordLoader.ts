import {Record, RecordList, RecordSearch, RecordSet, RecordSetList} from "../../protobuf/generated/record_pb";
import {PlaceId} from "../places/Place";
import {queryParameters} from "../fetch/Fetch";
import {protoGet, protoPost} from "../fetch/ProtoFetch";
import {RecordSetId, sortRecordSetByTitle} from "./RecordSet";

export interface RecordLoader {

    loadRecords(id: RecordSetId, options?: RecordOptions): Promise<ReadonlyArray<Record.AsObject>>

    loadRecordSet(id: RecordSetId): Promise<Readonly<RecordSet.AsObject>>

    loadRecordSets(options?: RecordSetOptions): Promise<ReadonlyArray<RecordSet.AsObject>>;

    loadChildRecordSets(id: RecordSetId): Promise<ReadonlyArray<RecordSet.AsObject>>

    submitSearch(search: RecordSearch): Promise<ReadonlyArray<Record.AsObject>>

}

type RecordOptions = {start?: number, limit?: number}
type RecordSetOptions = {place?: PlaceId, limit?: number}

class ProtoRecordLoader implements RecordLoader {

    loadRecords(id: string, options: RecordOptions = {}) {
        const url = "/records/in/" + id + queryParameters(options);
        return protoGet(url, RecordList.deserializeBinary).then(readRecords);
    }

    loadRecordSet(id: string) {
        return protoGet("/records/sets/" + id, RecordSet.deserializeBinary)
            .then(s => s ? s.toObject() : null);
    }

    loadRecordSets(options) {
        const url = "/records/sets" + queryParameters(options);
        return protoGet(url, RecordSetList.deserializeBinary).then(readRecordSets);
    }

    loadChildRecordSets(id: string): Promise<ReadonlyArray<RecordSet.AsObject>> {
        return protoGet("/records/sets/children/" + id, RecordSetList.deserializeBinary).then(readRecordSets);
    }

    submitSearch(search: RecordSearch): Promise<ReadonlyArray<Record.AsObject>> {
        return protoPost("/records/search", search, RecordSearch.serializeBinaryToWriter, RecordList.deserializeBinary).then(readRecords);
    }

}

function readRecordSets(list: RecordSetList): RecordSet.AsObject[] {
    if (!list) return [];
    return list.getRecordsetList().map(l => l.toObject()).sort(sortRecordSetByTitle);
}

function readRecords(list: RecordList): Record.AsObject[] {
    if (!list) return [];
    return list.getRecordList().map(l => l.toObject());
}

export const DEFAULT_RECORD_LOADER: RecordLoader = new ProtoRecordLoader();