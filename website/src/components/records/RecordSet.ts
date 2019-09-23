import {RecordSet} from "../../protobuf/generated/record_pb";
import {sortDatesByEarliest} from "../date/DateRange";

export type RecordSetId = string;

export function sortRecordSetByTitle(r1: RecordSet.AsObject, r2: RecordSet.AsObject): number {
    return r1.longtitle.localeCompare(r2.longtitle, undefined, {numeric: true, sensitivity: "base"});
}

export function sortRecordSetByEarliestDate(r1: RecordSet.AsObject, r2: RecordSet.AsObject): number {
    return sortDatesByEarliest(r1.date, r2.date);
}

export function sortRecordSetByDateThenTitle(r1: RecordSet.AsObject, r2: RecordSet.AsObject): number {
    return sortRecordSetByEarliestDate(r1, r2) || sortRecordSetByTitle(r1, r2);
}