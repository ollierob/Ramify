import {Age, Period} from "../../protobuf/generated/age_pb";

export function renderAge(age: Age.AsObject): string {
    if (!age) return null;
    const min = render(age.min);
    const max = render(age.max);
    return min == max || !max ? min : min + " - " + max;
}

function render(period: Period.AsObject): string {
    if (!period) return null;
    if (period.years == 0) {
        if (period.months > 0) return period.months + "m";
        if (period.days > 0) return period.days + "d";
        return "0";
    }
    let years = period.years;
    if (period.months < 0 || period.days < 0) years--;
    return String(years);
}

export function sortAges(a1: Age.AsObject, a2: Age.AsObject): number {
    if (a1.min.years != a2.min.years) return a1.min.years - a2.min.years;
    if (a1.min.months != a2.min.months) return a1.min.months - a2.min.months;
    return a1.min.days - a2.min.days;
}