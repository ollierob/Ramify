import {Event} from "../../protobuf/generated/event_pb";
import {sortDatesByEarliest} from "../date/DateRange";

export type ProfileEvent = {
    event: Event.AsObject
    type: "person" | "family" | "historic"
}

export function sortProfileEvents(e1: ProfileEvent, e2: ProfileEvent): number {
    if (e1.type == "person" && e2.type == "person") return 0; //Retain current ordering
    return sortDatesByEarliest(e1.event.date, e2.event.date);
}