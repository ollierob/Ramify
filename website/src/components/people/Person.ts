import {Person} from "../../protobuf/generated/person_pb";
import {findBirth} from "../event/Event";
import {sortDatesByEarliest} from "../date/DateRange";

export function sortPeopleByBirthDate(p1: Person.AsObject, p2: Person.AsObject) {
    const b1 = findBirth(p1.eventsList);
    if (!b1) return -1;
    const b2 = findBirth(p2.eventsList);
    if (!b2) return 1;
    return sortDatesByEarliest(b1.date, b2.date) || sortPeopleByName(p1, p2);
}

export function sortPeopleByName(p1: Person.AsObject, p2: Person.AsObject) {
    return (p1.name && p1.name.value || "").localeCompare(p2.name && p2.name.value || "");
}