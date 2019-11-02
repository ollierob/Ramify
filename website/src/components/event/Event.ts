import {Event} from "../../protobuf/generated/event_pb";
import {RecordType, recordTypeFromValue} from "../records/RecordType";
import {earliestYear, latestYear} from "../date/DateRange";

export type EventType = RecordType;

export function isBirthEvent(event: Event.AsObject): boolean {
    return eventType(event) == "BIRTH";
}

export function isBaptismEvent(event: Event.AsObject): boolean {
    return eventType(event) == "BAPTISM";
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

export function isMentionEvent(event: Event.AsObject): boolean {
    return eventType(event) == "MENTION";
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

export function isPostDeathEvent(event: Event.AsObject): boolean {
    switch (eventType(event)) {
        case "BURIAL":
        case "PROBATE":
        case "WILL":
            return true;
        default:
            return false;
    }
}

export function eventType(event: Event.AsObject): EventType {
    return event ? recordTypeFromValue(event.type) : null;
}

export function birthYear(events: ReadonlyArray<Event.AsObject>): number {
    const birth = events.find(isBirthEvent);
    if (!birth) return null;
    return earliestYear(birth.date);
}

export function deathYear(events: ReadonlyArray<Event.AsObject>): number {
    const death = events.find(isDeathEvent);
    if (!death) return null;
    return latestYear(death.date);
}