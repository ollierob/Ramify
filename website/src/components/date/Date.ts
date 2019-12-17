import {Date as DateProto} from "../../protobuf/generated/date_pb";

export function areDatesEqual(d1: DateProto.AsObject, d2: DateProto.AsObject) {
    if (d1 == null) return d2 == null;
    if (d2 == null) return d1 == null;
    return d1.year == d2.year
        && d1.month == d2.month
        && d1.day == d2.day;
}