import {RecordSearch} from "../../protobuf/generated/record_pb";

export type RecordSearchHandler = {
    searching: boolean;
    search: RecordSearch;
    doSearch: (search: RecordSearch) => void;
}