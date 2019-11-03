import {Person} from "../../protobuf/generated/person_pb";

export function isMale(person: Person.AsObject): boolean {
    return person != null && person.gender.toLowerCase() == "male";
}

export function isFemale(person: Person.AsObject): boolean {
    return person != null && person.gender.toLowerCase() == "female";
}