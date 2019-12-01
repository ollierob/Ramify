import {Date} from "../../protobuf/generated/date_pb";
import {YearFormatter} from "./DateFormatter";

test("YearFormatter", () => {

    const d1: Date.AsObject = {year: 1900, month: 1, day: 1};
    const d2: Date.AsObject = {year: 1901, month: 1, day: 1};
    const d3: Date.AsObject = {year: 1902, month: 1, day: 1};

    expect(YearFormatter.formatRange(d1, d1)).toEqual("1900");
    expect(YearFormatter.formatRange(d1, d2)).toEqual("1900/01");

});