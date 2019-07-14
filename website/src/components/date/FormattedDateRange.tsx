import * as React from "react";
import {DateRange as DateRangeProto} from "../../protobuf/generated/date_pb";
import {DateFormatter, DayMonthYearFormatter, MonthYearFormatter, YearFormatter} from "./DateFormatter";
import {datesEqual, yearEquals} from "./DateRange";

export const FormattedYearRange = (props: {date: DateRangeProto.AsObject, words?: PrefixWords}) => <FormattedDateRange {...props} accuracy="year"/>;

export const FormattedDateRange = (props: {date: DateRangeProto.AsObject, words?: PrefixWords, accuracy: "year" | "month" | "day"}) => {
    const date = props.date;
    if (!date) return null;
    const words = props.words || DefaultPrefixWords;
    const format = dateFormatter(props.accuracy);
    if (!date.earliest && !date.latest) return <>{words.unknown}</>;
    if (date.earliest && !date.latest) return <>{words.after} {format(date.earliest)}</>;
    if (!date.earliest && date.latest) return <>by {format(date.latest)}</>;
    if (props.accuracy == "year" && yearEquals(date.earliest, date.latest)) return <>{words.in}{format(date.earliest)}</>;
    if (datesEqual(date.earliest, date.latest)) return <>{words.on}{format(date.earliest)}</>;
    return <>{format(date.earliest)} - {format(date.latest)}</>;
};

function dateFormatter(accuracy: "year" | "month" | "day"): DateFormatter {
    switch (accuracy) {
        case "year":
            return YearFormatter;
        case "month":
            return MonthYearFormatter;
        case "day":
            return DayMonthYearFormatter;
    }
}

export type PrefixWords = {
    in: string;
    on: string;
    after: string;
    unknown: string;
}

export const EmptyPlaceWords: PrefixWords = {
    in: "",
    on: "",
    unknown: null,
    after: ""
};

const DefaultPrefixWords: PrefixWords = {
    in: "in ",
    on: "on ",
    unknown: "unknown",
    after: "after "
};