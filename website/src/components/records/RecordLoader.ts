import {
    IndividualRecord,
    IndividualRecordList,
    Record,
    RecordImageList,
    RecordList,
    RecordSearch,
    RecordSet,
    RecordSetHierarchy,
    RecordSetList,
    RecordSetRelatives
} from "../../protobuf/generated/record_pb";
import {PlaceId} from "../places/Place";
import {queryParameters} from "../fetch/Fetch";
import {protoGet, protoPost} from "../fetch/ProtoFetch";
import {RecordSetId, sortRecordSetByDateThenTitle} from "./RecordSet";
import {buildFamilyRecords, FamilyRecord} from "../../pages/records/set/family/FamilyRecord";
import {DateRange} from "../date/DateRange";

export interface RecordLoader {

    loadFamilyRecords(id: RecordSetId, options?: RecordOptions): Promise<ReadonlyArray<FamilyRecord>>

    loadIndividualRecords(id: RecordSetId, options?: RecordOptions): Promise<ReadonlyArray<EnrichedIndividualRecord>>

    searchIndividualRecords(search: RecordSearch): Promise<ReadonlyArray<EnrichedIndividualRecord>>

    loadRecordSet(id: RecordSetId): Promise<Readonly<RecordSet.AsObject>>

    loadRecordSets(options?: RecordSetOptions): Promise<ReadonlyArray<RecordSet.AsObject>>;

    loadRecordSetRelatives(id: RecordSetId): Promise<RecordSetRelatives.AsObject>

    loadRecordSetHierarchy(id: RecordSetId): Promise<RecordSetHierarchy.AsObject>

    loadRecordImages(id: RecordSetId): Promise<RecordImageList.AsObject>

}

type RecordOptions = {start?: number, limit?: number, children?: boolean}
export type RecordSetOptions = {name?: string, place?: PlaceId, limit?: number, fromDate?: string, toDate?: string, onlyParents?: boolean}
export type EnrichedIndividualRecord = IndividualRecord.AsObject & {recordSet: RecordSet.AsObject};

class ProtoRecordLoader implements RecordLoader {

    loadFamilyRecords(id: string, options: RecordOptions = {}) {
        const url = "/records/in/" + id + queryParameters(options);
        return protoGet(url, RecordList.deserializeBinary).then(buildFamilyRecords);
    }

    loadIndividualRecords(id: string, options: RecordOptions = {}) {
        const url = "/records/individuals/in/" + id + queryParameters(options);
        return protoGet(url, IndividualRecordList.deserializeBinary)
            .then(readIndividualRecords);
    }

    searchIndividualRecords(search: RecordSearch) {
        return protoPost("/records/individuals/search", search, RecordSearch.serializeBinaryToWriter, IndividualRecordList.deserializeBinary)
            .then(readIndividualRecords);
    }

    loadRecordSet(id: string) {
        return protoGet("/records/sets/at/" + id, RecordSet.deserializeBinary)
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

    loadRecordImages(id: RecordSetId) {
        return protoGet("/records/images/set/" + id + "?includeChildren=true", RecordImageList.deserializeBinary)
            .then(l => l ? l.toObject() : null);
    }

}

function readRecords(list: RecordList): Record.AsObject[] {
    if (!list) return [];
    return list.getRecordList().map(l => l.toObject());
}

function readRecordSets(list: RecordSetList): RecordSet.AsObject[] {
    if (!list) return [];
    return list.getRecordsetList().map(l => l.toObject()).sort(sortRecordSetByDateThenTitle);
}

function readIndividualRecords(list: IndividualRecordList): EnrichedIndividualRecord[] {
    if (!list) return [];
    const recordSets = list.getRecordsetsMap();
    return list.getRecordList().map(l => l.toObject()).map<EnrichedIndividualRecord>(o => ({...o, recordSet: recordSets.get(o.recordsetid).toObject()}));
}

export const DEFAULT_RECORD_LOADER: RecordLoader = new ProtoRecordLoader();