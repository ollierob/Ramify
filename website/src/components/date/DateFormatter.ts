import {Date as DateProto} from "../../protobuf/generated/date_pb";

export type DateFormatter = (date: DateProto.AsObject) => string | number;

export const YearFormatter: DateFormatter = d => d.year;
export const MonthYearFormatter: DateFormatter = d => d.month + "/" + d.year;
export const DayMonthYearFormatter: DateFormatter = d => d.day + "/" + d.month + "/" + d.year;
