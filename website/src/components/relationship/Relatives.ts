import {Person} from "../../protobuf/generated/person_pb";
import {Family} from "../../protobuf/generated/family_pb";
import {PersonId} from "../people/PersonId";
import {findFather, findMother} from "./Family";

export type Relatives = {
    father?: Person.AsObject
    mother?: Person.AsObject
    spouses: SpouseAndChildren[]
}

type SpouseAndChildren = {
    spouse: Person.AsObject;
    children: ReadonlyArray<Person.AsObject>
}

export function determineRelatives(person: PersonId, family: Family.AsObject): Relatives {
    if (!person || !family) return null;
    const relatives: Relatives = {spouses: []};
    relatives.father = findFather(person, family);
    relatives.mother = findMother(person, family);
    //TODO spouses/children
    return relatives;
}