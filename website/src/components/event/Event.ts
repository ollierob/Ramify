import {Event} from "../../protobuf/generated/event_pb";
import {RecordType, recordTypeFromValue} from "../records/RecordType";
import {DateRange, earliestYear, latestYear} from "../date/DateRange";
import {Person} from "../../protobuf/generated/person_pb";
import {Family} from "../../protobuf/generated/family_pb";
import {Date} from "../../protobuf/generated/date_pb";
import {maxDate, minDate} from "../date/Date";

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

export function isHistoricEvent(event: Event.AsObject): boolean {
    return eventType(event) == "HISTORIC";
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

export function findBirth(events: ReadonlyArray<Event.AsObject>): Event.AsObject {
    return events ? events.find(isBirthEvent) : null;
}

export function findDeath(events: ReadonlyArray<Event.AsObject>): Event.AsObject {
    return events ? events.find(isDeathEvent) : null;
}

export function findOtherEventPeople(person: Person.AsObject, family: Family.AsObject, event: Event.AsObject): ReadonlyArray<Person.AsObject> {
    const people: Person.AsObject[] = [];
    for (const p of family.personList) {
        if (person.id == p.id) continue;
        const e = p.eventsList.find(e => e.id == event.id);
        if (e) people.push(p);
    }
    return people;
}

export function eventDateRange(events: ReadonlyArray<Event.AsObject>): DateRange {
    switch (events.length) {
        case 0:
            return null;
        case 1:
            return events[0].date;
        default:
            let earliest: Date.AsObject = null;
            let latest: Date.AsObject = null;
            for (const event of events) {
                if (!event.date) continue;
                earliest = minDate(earliest, event.date.earliest);
                latest = maxDate(latest, event.date.latest);
            }
            return {earliest, latest, approximate: false};
    }
}