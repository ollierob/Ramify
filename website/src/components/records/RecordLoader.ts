import {IndividualRecord, IndividualRecordList, RecordImageList, RecordSearch, RecordSet, RecordSetHierarchy, RecordSetList, RecordSetRelatives} from "../../protobuf/generated/record_pb";
import {PlaceId} from "../places/Place";
import {queryParameters} from "../fetch/Fetch";
import {protoGet, protoPost} from "../fetch/ProtoFetch";
import {RecordSetId, sortRecordSetByDateThenTitle} from "./RecordSet";

export interface RecordLoader {

    loadIndividualRecords(id: RecordSetId, options?: RecordOptions): Promise<ReadonlyArray<EnrichedIndividualRecord>>

    searchIndividualRecords(search: RecordSearch): Promise<ReadonlyArray<EnrichedIndividualRecord>>

    loadRecordSet(id: RecordSetId): Promise<Readonly<RecordSet.AsObject>>

    loadRecordSets(options?: RecordSetOptions): Promise<ReadonlyArray<RecordSet.AsObject>>;

    loadRecordSetRelatives(id: RecordSetId): Promise<RecordSetRelatives.AsObject>

    loadRecordSetHierarchy(id: RecordSetId): Promise<RecordSetHierarchy.AsObject>

    loadRecordImages(id: RecordSetId): Promise<RecordImageList.AsObject>

}

type RecordOptions = {start?: number, limit?: number, children?: boolean}
export type RecordSetOptions = {name?: string, place?: PlaceId, limit?: number, onlyParents?: boolean}
export type EnrichedIndividualRecord = IndividualRecord.AsObject & {recordSet: RecordSet.AsObject};

class ProtoRecordLoader implements RecordLoader {

    loadIndividualRecords(id: string, options: RecordOptions = {}) {
        const url = "/records/individuals/in/" + id + queryParameters(options);
        return protoGet(url, IndividualRecordList.deserializeBinary)
            .then(readRecords);
    }

    searchIndividualRecords(search: RecordSearch) {
        return protoPost("/records/individuals/search", search, RecordSearch.serializeBinaryToWriter, IndividualRecordList.deserializeBinary)
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

    loadRecordSetRelatives(id: RecordSetId) {
        return protoGet("/records/sets/relatives/" + id, RecordSetRelatives.deserializeBinary)
            .then(r => r ? r.toObject() : null);
    }

    loadRecordSetHierarchy(id: RecordSetId) {
        return protoGet("/records/sets/hierarchy/" + id, RecordSetHierarchy.deserializeBinary)
            .then(r => r ? r.toObject() : null);
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

function readRecords(list: IndividualRecordList): EnrichedIndividualRecord[] {
    if (!list) return [];
    const recordSets = list.getRecordsetsMap();
    return list.getRecordList().map(l => l.toObject()).map<EnrichedIndividualRecord>(o => ({...o, recordSet: recordSets.get(o.recordsetid).toObject()}));
}

export const DEFAULT_RECORD_LOADER: RecordLoader = new ProtoRecordLoader();