import {RecordSet} from "../../protobuf/generated/record_pb";
import * as React from "react";

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

export const RecordSetLink = (props: {recordSet: RecordSet.AsObject, newWindow?: boolean}) => {
    if (!props.recordSet) return null;
    return <a href={recordSetHref(props.recordSet)} className="recordSet" target={props.newWindow ? "_blank" : "_self"}>
        {props.recordSet.longtitle}
    </a>;
};