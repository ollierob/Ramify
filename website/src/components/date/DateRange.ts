import {Date as DateProto, DateRange as DateRangeProto} from "../../protobuf/generated/date_pb";

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

export function isoDate(date: DateProto.AsObject): string {
    return date.year + "-" + date.month + "-" + date.day;
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