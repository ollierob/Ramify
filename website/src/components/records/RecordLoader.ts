import {IndividualRecord, IndividualRecordList, Record, RecordImageList, RecordSearch, RecordSet, RecordSetList} from "../../protobuf/generated/record_pb";
import {PlaceId} from "../places/Place";
import {queryParameters} from "../fetch/Fetch";
import {protoGet, protoPost} from "../fetch/ProtoFetch";
import {RecordSetId, sortRecordSetByDateThenTitle} from "./RecordSet";

export interface RecordLoader {

    loadRecords(id: RecordSetId, options?: RecordOptions): Promise<ReadonlyArray<IndividualRecord.AsObject>>

    submitSearch(search: RecordSearch): Promise<ReadonlyArray<IndividualRecord.AsObject>>

    loadRecordSet(id: RecordSetId): Promise<Readonly<RecordSet.AsObject>>

    loadRecordSets(options?: RecordSetOptions): Promise<ReadonlyArray<RecordSet.AsObject>>;

    loadChildRecordSets(id: RecordSetId): Promise<ReadonlyArray<RecordSet.AsObject>>

    loadRecordImages(id: RecordSetId): Promise<RecordImageList.AsObject>

}

type RecordOptions = {start?: number, limit?: number, children?: boolean}
export type RecordSetOptions = {name?: string, place?: PlaceId, limit?: number, onlyParents?: boolean}

class ProtoRecordLoader implements RecordLoader {

    loadRecords(id: string, options: RecordOptions = {}) {
        const url = "/records/in/" + id + queryParameters(options);
        return protoGet(url, IndividualRecordList.deserializeBinary)
            .then(readRecords);
    }

    loadRecordSet(id: string) {
        return protoGet("/records/sets/" + id, RecordSet.deserializeBinary)
            .then(s => s ? s.toObject() : null);
    }

    loadRecordSets(options: RecordSetOptions) {
        const url = "/records/sets" + queryParameters(options);
        return protoGet(url, RecordSetList.deserializeBinary).then(readRecordSets);
    }

    loadChildRecordSets(id: string): Promise<ReadonlyArray<RecordSet.AsObject>> {
        return protoGet("/records/sets/children/" + id, RecordSetList.deserializeBinary).then(readRecordSets);
    }

    submitSearch(search: RecordSearch) {
        return protoPost("/records/search", search, RecordSearch.serializeBinaryToWriter, IndividualRecordList.deserializeBinary)
            .then(readRecords);
    }

    loadRecordImages(id: string): Promise<RecordImageList.AsObject> {
        return protoGet("/records/images/" + id, RecordImageList.deserializeBinary)
            .then(l => l ? l.toObject() : null);
    }

}

function readRecordSets(list: RecordSetList): RecordSet.AsObject[] {
    if (!list) return [];
    return list.getRecordsetList().map(l => l.toObject()).sort(sortRecordSetByDateThenTitle);
}

function readRecords(list: IndividualRecordList): IndividualRecord.AsObject[] {
    if (!list) return [];
    return list.getRecordList().map(l => l.toObject());
}

export const DEFAULT_RECORD_LOADER: RecordLoader = new ProtoRecordLoader();