import {DateRange as DateRangeProto} from "../../protobuf/generated/date_pb";

export type DateRange = DateRangeProto.AsObject;
export type YearRange = {from: number, to: number}
