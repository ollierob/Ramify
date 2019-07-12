import * as React from "react";
import {Date as DateProto, DateRange as DateRangeProto} from "../../protobuf/generated/date_pb";

export const FormattedDateRange = (props: {date: DateRangeProto.AsObject, words?: PrefixWords}) => {
    const date = props.date;
    if (!date) return null;
    const words = props.words || DefaultPrefixWords;
    if (!date.earliest && !date.latest) return <>{words.unknown}</>;
    if (date.earliest && !date.latest) return <>{words.after} {format(date.earliest)}</>;
    if (!date.earliest && date.latest) return <>by {format(date.latest)}</>;
    if (yearEquals(date.earliest, date.latest)) return <>{words.in}{format(date.earliest)}</>;
    return <>{format(date.earliest)} - {format(date.latest)}</>;
};

export type PrefixWords = {
    in: string;
    after: string;
    unknown: string;
}

export const EmptyPlaceWords: PrefixWords = {
    in: "",
    unknown: null,
    after: ""
}

const DefaultPrefixWords: PrefixWords = {
    in: "in ",
    unknown: "unknown",
    after: "after "
};

function format(date: DateProto.AsObject) {
    return date.year;
}

function yearEquals(d1: DateProto.AsObject, d2: DateProto.AsObject): boolean {
    return d1.year == d2.year;
}