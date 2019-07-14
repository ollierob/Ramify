import {Event} from "../../protobuf/generated/event_pb";
import {RecordType, recordTypeFromValue} from "../records/RecordType";

export type EventType = RecordType;

export function isBirthEvent(event: Event.AsObject): boolean {
    return eventType(event) == "BIRTH";
}

export function isDeathEvent(event: Event.AsObject): boolean {
    return eventType(event) == "DEATH";
}

export function isBurialEvent(event: Event.AsObject): boolean {
    return eventType(event) == "BURIAL";
}

export function isDeathOrBurialEvent(event: Event.AsObject): boolean {
    return isDeathEvent(event) || isBurialEvent(event);
}

export function eventType(event: Event.AsObject): EventType {
    return event ? recordTypeFromValue(event.type) : null;
}