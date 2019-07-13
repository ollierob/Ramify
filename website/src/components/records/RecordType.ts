import {RecordTypeMap, RecordType as RecordTypes} from "../../protobuf/generated/record_pb";

export type RecordType = keyof RecordTypeMap;

const RecordTypeIndexes = recordTypeIndexes();

function recordTypeIndexes(): {[k: number]: RecordType} {
    const m: {[k: number]: RecordType} = {};
    Object.keys(RecordTypes).map(k => k as keyof RecordTypeMap).forEach(k => m[RecordTypes[k]] = k);
    return m;
}

export function recordTypeFromValue(key: RecordTypeMap[keyof RecordTypeMap]): RecordType {
    return RecordTypeIndexes[key];
}