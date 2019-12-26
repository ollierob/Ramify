import {Record, RecordList, RecordSet} from "../../../../protobuf/generated/record_pb";
import {DateRange} from "../../../../protobuf/generated/date_pb";
import {RecordType, recordTypeFromValue} from "../../../../components/records/RecordType";
import {Family} from "../../../../protobuf/generated/family_pb";
import {Place} from "../../../../protobuf/generated/place_pb";
import {stringMap} from "../../../../components/Maps";

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

export function buildFamilyRecords(records: RecordList): FamilyRecord[] {
    if (!records) return [];
    const recordSets = stringMap(records.getRecordsetsList().map(r => r.toObject()), r => r.id);
    return flatten(records.getRecordList().map(record => buildFamilyRecord(record.toObject(), recordSets[record.getRecordsetid()])));
}

function buildFamilyRecord(record: Record.AsObject, recordSet: RecordSet.AsObject): FamilyRecord[] {
    return record.familyList.map(family => ({
        ...record,
        date: record.date,
        type: recordTypeFromValue(record.type),
        family: family,
        recordSet: recordSet
    }));
}