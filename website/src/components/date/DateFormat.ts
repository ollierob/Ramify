import {Date as DateProto, DateRange as DateRangeProto} from "../../protobuf/generated/date_pb";
import {DateFormatter, DayMonthYearFormatter, MonthYearFormatter, YearFormatter} from "./DateFormatter";
import {datesToRange, earliestYear, isStartOfYear, isWholeYear, latestYear} from "./DateRange";

type FormatType = "year" | "month" | "day";

export type PrefixWords = {
    before: string;
    after: string;
    in: string
    on: string
    unknown: string
    between: string
    approximate: string
}

export const DefaultPrefixWords: PrefixWords = {
    unknown: "?",
    before: "before ",
    after: "after ",
    in: "in ",
    on: "on ",
    between: "",
    approximate: "c."
};

export const EmptyPrefixWords: PrefixWords = {
    unknown: "",
    before: "",
    after: "",
    in: "",
    on: "",
    between: "",
    approximate: "c."
};

function formatter(type: FormatType | DateFormatter): DateFormatter {
    if (typeof type != "string") return type;
    switch (type) {
        case "year":
        default:
            return YearFormatter;
        case "month":
            return MonthYearFormatter;
        case "day":
            return DayMonthYearFormatter;
    }
}

export function formatYear(d: DateProto.AsObject): string {
    return formatDate(d, "year");
}

export function formatDate(d: DateProto.AsObject, type: FormatType | DateFormatter): string {
    if (!d) return null;
    return formatter(type).formatDate(d);
}

export function formatShortYearRange(r: DateRangeProto.AsObject, words?: Partial<PrefixWords>, preferEarly?: boolean): string {
    words = words ? {...DefaultPrefixWords, ...words} : DefaultPrefixWords;
    const y1 = earliestYear(r);
    const y2 = latestYear(r);
    if (y1 == y2) return words.in + y1;
    if (y1 + 1 == y2) return words.in + y1 + "/" + (y2 % 10);
    return preferEarly ? formatYear(r.earliest) : formatYear(r.latest);
}

export function formatYearRange(r: DateRangeProto.AsObject, words?: Partial<PrefixWords>): string {
    return formatDateRange(r, "year", words);
}

function ensureWords(words: Partial<PrefixWords>): PrefixWords {
    return words ? {...DefaultPrefixWords, ...words} : DefaultPrefixWords;
}

function ensureWord(words: Partial<PrefixWords>, key: keyof PrefixWords): string {
    return words[key] || DefaultPrefixWords[key];
}

export function formatDateRange(r: DateRangeProto.AsObject, type: FormatType | DateFormatter, words?: Partial<PrefixWords>): string {
    if (!r) return null;
    words = ensureWords(words);
    if (!r.earliest && !r.latest) return words.unknown;
    const format = formatter(type);
    const approx = r.approximate ? words.approximate : "";
    if (!r.earliest) return words.after + approx + format.formatDate(r.latest);
    if (!r.latest) return words.before + approx + format.formatDate(r.earliest);
    switch (type) {
        case "day":
            if (isoDate(r.earliest) == isoDate(r.latest)) return words.on + approx + format.formatDate(r.earliest);
            if (isWholeYear(r)) return words.in + approx + r.earliest.year;
            break;
        case "month":
            if (isoDate(r.earliest, 1) == isoDate(r.latest, 1)) return words.in + approx + format.formatDate(r.earliest);
            break;
        case "year":
            if (earliestYear(r) == latestYear(r)) return words.in + approx + earliestYear(r);
            break;
    }
    return words.between + approx + format.formatRange(r.earliest, r.latest);
}

export function formatYearRanges(r1: DateRangeProto.AsObject, r2: DateRangeProto.AsObject, words?: Partial<PrefixWords>) {
    return formatDateRanges(r1, r2, "year", words);
}

export function formatDateRanges(r1: DateRangeProto.AsObject, r2: DateRangeProto.AsObject, type: FormatType, words?: Partial<PrefixWords>): string {
    if (!r1 && !r2) return null;
    if (!r1) return ensureWord(words, "before") + formatDateRange(r2, type, EmptyPrefixWords);
    if (!r2) return ensureWord(words, "after") + formatDateRange(r1, type, EmptyPrefixWords);
    if (type == "year") {
        const y1 = formatShortYearRange(r1, EmptyPrefixWords, true);
        const y2 = formatShortYearRange(r2, EmptyPrefixWords, false);
        return y1 == y2 ? y1 : y1 + " - " + y2;
    }
    return formatDateRange(datesToRange(r1.earliest, r2.latest, r1.approximate || r2.approximate), type, words);
}

export function isoDate(date: DateProto.AsObject, d?: number): string {
    return date.year + "-" + zeroPad(date.month, 2) + "-" + zeroPad(d || date.day, 2);
}

function zeroPad(n: number, length: number = 2): string {
    let s = String(n);
    while (s.length < length) s = '0' + s;
    return s;
}