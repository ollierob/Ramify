import {RecordSet} from "../../protobuf/generated/record_pb";
import {sortDateByEarliest} from "../date/DateRange";

export type RecordSetId = string;

export function sortRecordSetByTitle(r1: RecordSet.AsObject, r2: RecordSet.AsObject): number {
    return r1.longtitle.localeCompare(r2.longtitle);
}

export function sortRecordSetByEarliestDate(r1: RecordSet.AsObject, r2: RecordSet.AsObject): number {
    return sortDateByEarliest(r1.date, r2.date);
}