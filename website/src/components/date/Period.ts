import {Period} from "../../protobuf/generated/age_pb";

export function periodToString(period: Period.AsObject): string {
    if (!period) return null;
    if (period.years == 0) {
        if (period.months > 0) return period.months + "m";
        if (period.days > 0) return period.days + "d";
        return "0";
    }
    let years = period.years;
    if (period.months == 0 && period.days == -1) years--;
    else if (period.months == -1 && period.days == 0) years--;
    return String(years);
}