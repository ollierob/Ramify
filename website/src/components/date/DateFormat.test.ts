import {formatDateRange, PrefixWords} from "./DateFormat";
import {DateRange} from "../../protobuf/generated/date_pb";

test("formatDateRange_WholeYear", () => {

    const year: DateRange.AsObject = {approximate: false, earliest: {year: 1900, month: 1, day: 1}, latest: {year: 1900, month: 12, day: 31}};
    const words: Partial<PrefixWords> = {in: "in "};

    expect(formatDateRange(year, "day", words)).toBe("in 1900");
    expect(formatDateRange(year, "month", words)).toBe("in 1900");
    expect(formatDateRange(year, "year", words)).toBe("in 1900");

});