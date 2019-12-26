import {RecordSearch} from "../../protobuf/generated/record_pb";
import {RecordSetOptions} from "../records/RecordLoader";
import {QueryMap} from "../Page";
import {StringMap} from "../Maps";

export type RecordSearchHandler = {
    searching: boolean;
    search: RecordSearch;
    doSearch: (search: RecordSearch) => void;
}

export function recordSearchToHash(options: RecordSetOptions): QueryMap {
    return {
        base: "search",
        searchName: options.name,
        searchPlace: options.place,
        searchFromDate: options.fromDate,
        searchToDate: options.toDate,
    };
}

export function hashToRecordSearch(map: StringMap): RecordSetOptions {
    return {
        name: map.searchName,
        fromDate: map.searchFromDate,
        toDate: map.searchToDate
    };
}