import {Date, Date as DateProto} from "../../protobuf/generated/date_pb";
import {datesEqual} from "./DateRange";
import {isoDate} from "./DateFormat";

export function areDatesEqual(d1: DateProto.AsObject, d2: DateProto.AsObject) {
    if (d1 == null) return d2 == null;
    if (d2 == null) return false;
    return d1.year == d2.year
        && d1.month == d2.month
        && d1.day == d2.day;
}

export function areMonthsEqual(d1: DateProto.AsObject, d2: DateProto.AsObject) {
    if (d1 == null) return d2 == null;
    if (d2 == null) return false;
    return d1.year == d2.year
        && d1.month == d2.month
        && d1.day <= d2.day;
}

export function minDate(d1: Date.AsObject, d2: Date.AsObject): Date.AsObject {
    if (!d1) return d2;
    if (!d2) return d1;
    if (d1.year != d2.year) return d1.year < d2.year ? d1 : d2;
    if (d1.month != d2.month) return d1.month < d2.month ? d1 : d2;
    return d1.day <= d2.day ? d1 : d2;
}

export function maxDate(d1: Date.AsObject, d2: Date.AsObject): Date.AsObject {
    if (!d1) return d2;
    if (!d2) return d1;
    return minDate(d1, d2) == d1 ? d2 : d1;
}

export function lessThanOrEqual(d1: Date.AsObject, d2: Date.AsObject): boolean {
    return minDate(d1, d2) == d1 || datesEqual(d1, d2);
}

export function atStartOfYear(year: number): Date.AsObject {
    return {year, month: 1, day: 1};
}

export function atEndOfYear(year: number): Date.AsObject {
    return {year, month: 12, day: 31};
}

export function toIsoDate(d: Date.AsObject): string {
    return isoDate(d);
}

export function parseIsoDate(date: string): Date.AsObject {
    if (!date || date.length != 10) return null;
    const year = parseFloat(date.substr(0, 4));
    if (!year) return null;
    const month = parseFloat(date.substr(5, 7));
    if (!month) return null;
    const day = parseFloat(date.substr(8, 10));
    return {year, month, day};
}