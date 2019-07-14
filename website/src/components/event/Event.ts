import {Event} from "../../protobuf/generated/event_pb";
import {RecordType, recordTypeFromValue} from "../records/RecordType";

export type EventType = RecordType;

export function isBirthEvent(event: Event.AsObject): boolean {
    return eventType(event) == "BIRTH";
}

export function isResidenceEvent(event: Event.AsObject): boolean {
    switch (eventType(event)) {
        case "BAPTISM":
        case "BIRTH":
        case "MARRIAGE":
        case "DEATH":
        case "BURIAL":
        case "WILL":
            return false;
        default:
            return event.place != null;
    }
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