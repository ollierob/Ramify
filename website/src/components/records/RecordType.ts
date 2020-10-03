import {RecordTypeMap, RecordType as RecordTypes} from "../../protobuf/generated/event_pb";
import {RecordSet} from "../../protobuf/generated/record_pb";

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

export function isBirthOrBaptismRecord(recordSet: RecordSet.AsObject): boolean {
    const type = recordSet.type;
    return type == RecordTypes.BAPTISM || type == RecordTypes.BIRTH;
}

export function recordTypeName(record: RecordSet.AsObject | RecordType): string {
    const type: RecordType = typeof record == "string" ? record : recordTypeFromValue(record.type);
    return ShortRecordTitles[type];
}

const ShortRecordTitles: { [key in RecordType]: string } = {
    UNSPECIFIED: "Miscellaneous",
    BAPTISM: "Baptisms",
    BANNS: "Banns",
    BIRTH: "Births",
    BURIAL: "Burials",
    CENSUS: "Census",
    DEATH: "Deaths",
    DIVORCE: "Divorce",
    HISTORIC: "Historic",
    MARRIAGE: "Marriages",
    MEMBERSHIP: "Membership",
    MEMORIAL: "Memorials",
    MENTION: "Mentions",
    MILITARY: "Military Service",
    MIXED: "Mixed",
    PAYMENT: "Payments",
    PROBATE: "Probate",
    RESIDENCE: "Residence",
    TRANSACTION: "Transactions",
    WILL: "Wills"
};

export function recordTypes(includeMixed: boolean = true): RecordType[] {
    return Object.keys(RecordTypes)
        .map(k => k as RecordType)
        .filter(t => includeMixed || (t != "MIXED" && t != "HISTORIC"));
}

const RecordTypesSet: Set<string> = new Set(Object.keys(RecordTypes));

export function sanitizeRecordType(type: string): RecordType {
    if (!type) return null;
    type = type.trim().toUpperCase();
    return RecordTypesSet.has(type.toUpperCase()) ? type as RecordType : null;
}