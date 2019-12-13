import {Person} from "../../protobuf/generated/person_pb";
import {Family} from "../../protobuf/generated/family_pb";
import {PersonId} from "../people/PersonId";
import {findFather, findMother} from "./Family";

const flatten = require('arr-flatten');

export type Relatives = {
    father?: Person.AsObject
    mother?: Person.AsObject
    spouses: SpouseAndChildren[]
}

export function allRelatives(relatives: Relatives): ReadonlyArray<Person.AsObject> {
    if (!relatives) return [];
    const s = flatten(relatives.spouses.map(relativesWithSpouse));
    return [relatives.father, relatives.mother].concat(s).filter(p => p != null);
}

function relativesWithSpouse(spouse: SpouseAndChildren): ReadonlyArray<Person.AsObject> {
    return [spouse.spouse].concat(spouse.children);
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