import {RecordSet} from "../../protobuf/generated/record_pb";

export function recordSetHref(record: RecordSet.AsObject) {
    return recordsPrefix() + "#/set?id=" + record.id;
}

function recordsPrefix() {
    try {
        const current = window.location.pathname;
        if (current && !current.startsWith("/records")) return "/records";
    } catch (e) {
    }
    return "";
}