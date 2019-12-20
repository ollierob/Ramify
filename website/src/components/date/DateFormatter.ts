import {Date as DateProto} from "../../protobuf/generated/date_pb";
import {areDatesEqual} from "./Date";

//FIXME make this accept two values
export type DateFormatter = {
    formatDate: (date: DateProto.AsObject) => string;
    formatRange: (from: DateProto.AsObject, to: DateProto.AsObject) => string;
}

export const YearFormatter: DateFormatter = {
    formatDate: d => String(d.year),
    formatRange: (d1, d2) => d1.year == d2.year ? String(d1.year) : d1.year + " - " + d2.year
};

export const MonthYearFormatter: DateFormatter = {
    formatDate: formatMonthYear,
    formatRange: (d1, d2) => formatMonthYear(d1) + " - " + formatMonthYear(d2)
};

export const DayMonthYearFormatter: DateFormatter = {
    formatDate: formatDayMonthYear,
    formatRange: (d1, d2) => formatDayMonthYear(d1) + " - " + formatDayMonthYear(d2)
};

export const MonthDayYearFormatter: DateFormatter = {
    formatDate: formatMonthDayYear,
    formatRange: (d1, d2) => formatMonthDayYear(d1) + " - " + formatMonthDayYear(d2)
};

export const MonthDayWordsFormatter: DateFormatter = {
    formatDate: formatMonthDayWords,
    formatRange: (d1, d2) => {
        if (areDatesEqual(d1, d2)) return formatMonthDayWords(d1);
        return formatMonthDayWords(d1) + " - " + formatMonthDayWords(d2);
    }
};

function formatMonthYear(d: DateProto.AsObject) {
    return d.month + "/" + d.year;
}

function formatDayMonthYear(d: DateProto.AsObject) {
    return d.day + "/" + d.month + "/" + d.year;
}

function formatMonthDayYear(d: DateProto.AsObject) {
    return d.month + "/" + d.day + "/" + d.year;
}

function formatMonthDayWords(d: DateProto.AsObject) {
    return new Date(d.year, d.month-1, d.day).toLocaleDateString("default", {month: "short", day: "numeric"});
}
