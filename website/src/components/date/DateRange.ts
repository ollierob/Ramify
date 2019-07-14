import {Date as DateProto, DateRange as DateRangeProto} from "../../protobuf/generated/date_pb";

export type DateRange = DateRangeProto.AsObject;
export type YearRange = {from: number, to: number}

export function yearEquals(d1: DateProto.AsObject, d2: DateProto.AsObject): boolean {
    return d1.year == d2.year;
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