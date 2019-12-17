import {Age} from "../../protobuf/generated/age_pb";
import {periodToString} from "../date/Period";

export function renderAge(age: Age.AsObject): string {
    if (!age) return null;
    const min = periodToString(age.min);
    const max = periodToString(age.max);
    return min == max || !max ? min : min + " - " + max;
}

export function sortAges(a1: Age.AsObject, a2: Age.AsObject): number {
    if (a1.min.years != a2.min.years) return a1.min.years - a2.min.years;
    if (a1.min.months != a2.min.months) return a1.min.months - a2.min.months;
    return a1.min.days - a2.min.days;
}