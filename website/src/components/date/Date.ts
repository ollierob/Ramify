import {Date, Date as DateProto} from "../../protobuf/generated/date_pb";

export function areDatesEqual(d1: DateProto.AsObject, d2: DateProto.AsObject) {
    if (d1 == null) return d2 == null;
    if (d2 == null) return d1 == null;
    return d1.year == d2.year
        && d1.month == d2.month
        && d1.day == d2.day;
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
    if (d1.year != d2.year) return d1.year > d2.year ? d1 : d2;
    if (d1.month != d2.month) return d1.month > d2.month ? d1 : d2;
    return d1.day >= d2.day ? d1 : d2;
}