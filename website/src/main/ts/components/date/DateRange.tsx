import * as React from "react";
import {Date as DateProto, DateRange as DateRangeProto} from "../../protobuf/generated/date_pb";

export const DateRange = (props: {date: DateRangeProto.AsObject}) => {
    const date = props.date;
    if (!date) return null;
    if (date.earliest && !date.latest) return <>by {date.earliest}</>;
    return null;
}

function format(date: DateProto.AsObject) {
    return date.year;
}