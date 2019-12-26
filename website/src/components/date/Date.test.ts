import {Date} from "../../protobuf/generated/date_pb";
import {maxDate, minDate, parseIsoDate, toIsoDate} from "./Date";

test("minDate", () => {

    const d1: Date.AsObject = {year: 1900, month: 1, day: 1};
    const d2: Date.AsObject = {year: 1900, month: 1, day: 2};
    const d3: Date.AsObject = {year: 1900, month: 2, day: 1};
    const d4: Date.AsObject = {year: 1901, month: 1, day: 1};

    expect(minDate(null, d1)).toBe(d1);
    expect(minDate(d1, null)).toBe(d1);

    expect(minDate(d1, d1)).toBe(d1);
    expect(minDate(d1, d2)).toBe(d1);
    expect(minDate(d2, d3)).toBe(d2);
    expect(minDate(d3, d4)).toBe(d3);

});

test("maxDate", () => {

    const d1: Date.AsObject = {year: 1900, month: 1, day: 1};
    const d2: Date.AsObject = {year: 1900, month: 1, day: 2};
    const d3: Date.AsObject = {year: 1900, month: 2, day: 1};
    const d4: Date.AsObject = {year: 1901, month: 1, day: 1};

    expect(maxDate(null, d1)).toBe(d1);
    expect(maxDate(d1, null)).toBe(d1);

    expect(maxDate(d1, d1)).toBe(d1);
    expect(maxDate(d1, d2)).toBe(d2);
    expect(maxDate(d2, d3)).toBe(d3);
    expect(maxDate(d3, d4)).toBe(d4);

});

test("isoDate", () => {

    const d: Date.AsObject = {year: 1987, month: 6, day: 5};
    expect(toIsoDate(d)).toEqual("1987-06-05");
    expect(parseIsoDate("1987-06-05")).toEqual(d);

});