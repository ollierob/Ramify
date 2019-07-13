import {RecordSearch} from "../../protobuf/generated/record_pb";

export type RecordSearchHandler = {
    searching: boolean;
    doSearch: (search: RecordSearch) => void;
}