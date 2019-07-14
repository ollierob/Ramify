import {Event} from "../../protobuf/generated/event_pb";
import {RecordType, recordTypeFromValue} from "../records/RecordType";

export type EventType = RecordType;

export function isBirthEvent(event: Event.AsObject): boolean {
    return eventType(event) == "BIRTH";
}

export function isDeathEvent(event: Event.AsObject): boolean {
    return eventType(event) == "DEATH";
}

export function eventType(event: Event.AsObject): EventType {
    return event ? recordTypeFromValue(event.type) : null;
}