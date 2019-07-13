import {RecordSet} from "../../protobuf/generated/record_pb";

export type RecordSetId = string;

export function sortRecordSetByTitle(r1: RecordSet.AsObject, r2: RecordSet.AsObject): number {
    return r1.longtitle.localeCompare(r2.longtitle);
}