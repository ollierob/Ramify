import * as React from "react";
import {Date as DateProto, DateRange as DateRangeProto} from "../../protobuf/generated/date_pb";

export const DateRange = (props: {date: DateRangeProto.AsObject}) => {
    const date = props.date;
    if (!date) return null;
    if (!date.earliest && !date.latest) return <>unknown</>;
    if (date.earliest && !date.latest) return <>after {format(date.earliest)}</>;
    if (!date.earliest && date.latest) return <>by {format(date.latest)}</>;
    if (yearEquals(date.earliest, date.latest)) return <>in {format(date.earliest)}</>;
    return <>{format(date.earliest)} - {format(date.latest)}</>;
};

function format(date: DateProto.AsObject) {
    return date.year;
}

function yearEquals(d1: DateProto.AsObject, d2: DateProto.AsObject): boolean {
    return d1.year == d2.year;
}