import {Date as DateProto, DateRange as DateRangeProto} from "../../protobuf/generated/date_pb";
import {areDatesEqual, atEndOfYear, atStartOfYear, lessThanOrEqual} from "./Date";

export type DateRange = DateRangeProto.AsObject;
export type YearRange = {from: number, to: number}

export function yearEquals(d1: DateProto.AsObject, d2: DateProto.AsObject): boolean {
    return d1 && d2 && d1.year == d2.year;
}

export function monthsEqual(d1: DateProto.AsObject, d2: DateProto.AsObject): boolean {
    return d1.month == d2.month;
}

export function daysEqual(d1: DateProto.AsObject, d2: DateProto.AsObject): boolean {
    return d1.day == d2.day;
}

export function datesEqual(d1: DateProto.AsObject, d2: DateProto.AsObject): boolean {
    return yearEquals(d1, d2) && monthsEqual(d1, d2) && daysEqual(d1, d2);
}

export function sortDatesByEarliest(d1: DateRangeProto.AsObject, d2: DateRangeProto.AsObject): number {
    if (!d1 && !d2) return 0;
    if (!d1) return +1;
    if (!d2) return -1;
    return sortDates(d1.earliest || d1.latest, d2.earliest || d2.latest);
}

export function sortDatesByEarliestThenLatest(d1: DateRangeProto.AsObject, d2: DateRangeProto.AsObject): number {
    const d = sortDatesByEarliest(d1, d2);
    if (d == 0 && d1.latest && d2.latest) return sortDates(d1.earliest, d2.latest);
    return d;
}

export function sortDates(d1: DateProto.AsObject, d2: DateProto.AsObject): number {
    if (!d1 && !d2) return 0;
    if (d1.year != d2.year) return d1.year - d2.year;
    if (d1.month != d2.month) return d1.month - d2.month;
    return d1.day - d2.day;
}

export function earliestYear(date: DateRangeProto.AsObject): number {
    if (!date) return null;
    if (date.earliest) return date.earliest.year;
    if (date.latest) return date.latest.year;
    return null;
}

export function latestYear(date: DateRangeProto.AsObject): number {
    if (!date) return null;
    if (date.latest) return date.latest.year;
    if (date.earliest) return date.earliest.year;
    return null;
}

export function dateToRange(date: DateProto.AsObject): DateRangeProto.AsObject {
    return {earliest: date, latest: date, approximate: false};
}

export function datesToRange(d1: DateProto.AsObject, d2: DateProto.AsObject, approximate?: boolean): DateRangeProto.AsObject {
    if (!d1 && !d2) return null;
    if (d1 && d2 && d1 > d2) return datesToRange(d2, d1, approximate);
    return {earliest: d1, latest: d2, approximate: approximate};
}

export function isStartOfYear(d: DateProto.AsObject) {
    return d != null && d.day == 1 && d.month == 1;
}

export function isEndOfYear(d: DateProto.AsObject) {
    return d != null && d.month == 12 && d.day == 31;
}

export function isWholeYear(r: DateRangeProto.AsObject) {
    return isWholeYearRange(r) && r.earliest.year == r.latest.year;
}

export function isWholeYearRange(r: DateRangeProto.AsObject) {
    return !!r
        && r.earliest && r.latest
        && isStartOfYear(r.earliest)
        && isEndOfYear(r.latest);
}

export function isInMonth(r: DateRangeProto.AsObject) {
    return !!r
        && r.earliest && r.latest
        && r.earliest.year == r.latest.year
        && r.earliest.month == r.latest.month
        && r.earliest.day != r.latest.day;
}

export function isDateOrdered(d1: DateProto.AsObject, d2: DateRangeProto.AsObject, d3: DateProto.AsObject): boolean {
    return lessThanOrEqual(d1, d2.earliest) && lessThanOrEqual(d2.latest, d3);
}

export function isDateRangeOrdered(d1: DateRangeProto.AsObject, d2: DateRangeProto.AsObject, d3: DateRangeProto.AsObject): boolean {
    return sortDatesByEarliestThenLatest(d1, d2) <= 0 && sortDatesByEarliest(d2, d3) <= 0;
}

export function isExactRange(d: DateRangeProto.AsObject) {
    return d != null && areDatesEqual(d.earliest, d.latest);
}

export function yearsToDateRange(fromYear: number, toYear: number): DateRangeProto.AsObject {
    return {approximate: false, earliest: atStartOfYear(fromYear), latest: atEndOfYear(toYear)};
}

export function addYear(d: DateProto.AsObject, years: number = 1): DateProto.AsObject {
    if (!d || !years) return d;
    return {...d, year: d.year + years};
}