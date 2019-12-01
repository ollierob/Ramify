import {Date as DateProto} from "../../protobuf/generated/date_pb";
import {dateToRange, formatYearRange} from "./DateRange";

//FIXME make this accept two values
export type DateFormatter = {
    formatDate: (date: DateProto.AsObject) => string | number;
    formatRange: (from: DateProto.AsObject, to: DateProto.AsObject) => string | number
}

export const YearFormatter: DateFormatter = {
    formatDate: d => d.year,
    formatRange: (d1, d2) => formatYearRange(dateToRange(d1), dateToRange(d2))
};

export const MonthYearFormatter: DateFormatter = {
    formatDate: formatMonthYear,
    formatRange: (d1, d2) => formatMonthYear(d1) + " - " + formatMonthYear(d2)
};

export const DayMonthYearFormatter: DateFormatter = {
    formatDate: formatDayMonthYear,
    formatRange: (d1, d2) => formatDayMonthYear(d1) + " - " + formatDayMonthYear(d2)
};

function formatMonthYear(d: DateProto.AsObject) {
    return d.month + "/" + d.year;
}

function formatDayMonthYear(d: DateProto.AsObject) {
    return d.day + "/" + d.month + "/" + d.year;
}
