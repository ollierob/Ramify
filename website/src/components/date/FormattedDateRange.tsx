import * as React from "react";
import {DateRange as DateRangeProto} from "../../protobuf/generated/date_pb";
import {DateFormatter, DayMonthYearFormatter, MonthYearFormatter, YearFormatter} from "./DateFormatter";
import {datesEqual, yearEquals} from "./DateRange";

export const FormattedYearRange = (props: {date: DateRangeProto.AsObject, words?: Partial<PrefixWords>}) => {
    const words: Partial<PrefixWords> = {...props.words};
    if (!words.on) words.on = words.in || DefaultPrefixWords.in;
    return <FormattedDateRange {...props} words={words} accuracy="year"/>;
};

export const FormattedDateRange = (props: {date: DateRangeProto.AsObject, words?: Partial<PrefixWords>, accuracy: "year" | "month" | "day"}) => {
    const date = props.date;
    if (!date) return null;
    const words = props.words ? {...DefaultPrefixWords, ...props.words} : DefaultPrefixWords;
    const format = dateFormatter(props.accuracy);
    if (!date.earliest && !date.latest) return <>{words.unknown}</>;
    if (!date.earliest && date.latest) return <>{words.before}{format(date.latest)}</>;
    if (date.earliest && !date.latest) return <>{words.after}{format(date.earliest)}</>;
    if (datesEqual(date.earliest, date.latest)) return <>{words.on}{format(date.earliest)}</>;
    if (isInYear(date)) return <>{words.in}{date.earliest.year}</>;
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

function isInYear(date: DateRangeProto.AsObject): boolean {
    return yearEquals(date.earliest, date.latest)
        && date.earliest.month == 1
        && date.earliest.day == 1
        && date.latest.month == 12
        && date.latest.day == 31;
}

export type PrefixWords = {
    in: string;
    on: string;
    before: string;
    after: string;
    unknown: string;
}

export const EmptyPlaceWords: PrefixWords = {
    in: "",
    on: "",
    unknown: null,
    before: "",
    after: ""
};

const DefaultPrefixWords: PrefixWords = {
    in: "in ",
    on: "on ",
    unknown: "unknown",
    before: "before ",
    after: "after "
};