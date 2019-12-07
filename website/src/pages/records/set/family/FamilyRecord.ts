import {Record, RecordSet} from "../../../../protobuf/generated/record_pb";
import {DateRange} from "../../../../protobuf/generated/date_pb";
import {RecordType, recordTypeFromValue} from "../../../../components/records/RecordType";
import {Family} from "../../../../protobuf/generated/family_pb";
import {Place} from "../../../../protobuf/generated/place_pb";

const flatten = require('arr-flatten');

export type FamilyRecord = {
    id: string;
    date: DateRange.AsObject;
    type: RecordType;
    recordSet: RecordSet.AsObject;
    family: Family.AsObject;
    image?: string;
    place?: Place.AsObject
}

export function buildFamilyRecords(records: ReadonlyArray<Record.AsObject>): FamilyRecord[] {
    if (!records) return [];
    return flatten(records.map(buildFamilyRecord));
}

function buildFamilyRecord(record: Record.AsObject): FamilyRecord[] {
    return record.familyList.map(family => ({
        ...record,
        date: record.date,
        type: recordTypeFromValue(record.type),
        family: family,
        recordSet: null, //FIXME
        place: record.place
    }));
}