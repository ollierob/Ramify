import {Person} from "../../protobuf/generated/person_pb";
import {Family} from "../../protobuf/generated/family_pb";
import {PersonId} from "../people/PersonId";
import {findChildren, findFather, findMother, findSharedChildren, findSiblings, findSpouses} from "./Family";
import {Event} from "../../protobuf/generated/event_pb";

const flatten = require('arr-flatten');

export type Relatives = {
    father?: Person.AsObject
    mother?: Person.AsObject
    spouses: SpouseAndChildren[]
    siblings: Person.AsObject[]
}

type SpouseAndChildren = {
    spouse?: Person.AsObject;
    children: ReadonlyArray<Person.AsObject>
}

export function allRelatives(relatives: Relatives): ReadonlyArray<Person.AsObject> {
    if (!relatives) return [];
    const s = flatten(relatives.spouses.map(relativesWithSpouse));
    return [relatives.father, relatives.mother, ...relatives.siblings].concat(s).filter(p => p != null);
}

function relativesWithSpouse(spouse: SpouseAndChildren): ReadonlyArray<Person.AsObject> {
    if (!spouse.spouse) return spouse.children;
    return [spouse.spouse].concat(spouse.children);
}

export function determineRelatives(id: PersonId, family: Family.AsObject): Relatives {

    if (!id || !family) return null;

    const relatives: Relatives = {spouses: [], siblings: []};
    relatives.father = findFather(id, family);
    relatives.mother = findMother(id, family);

    const seenChildren = new Set<PersonId>();
    for (const spouse of findSpouses(id, family)) {
        const children = findSharedChildren(id, spouse.id, family);
        relatives.spouses.push({spouse, children: children});
        children.forEach(child => seenChildren.add(child.id));
    }

    const remainingChildren = findChildren(id, family).filter(child => !seenChildren.has(child.id));
    if (remainingChildren.length) relatives.spouses.push({children: remainingChildren});

    relatives.siblings = [...findSiblings(id, [relatives.father?.id, relatives.mother?.id], family)];

    return relatives;

}

export function findEventSpouse(relatives: Relatives, marriage: Event.AsObject): Person.AsObject {
    if (!relatives || !marriage) return null;
    for (const s of relatives.spouses) {
        if (s.spouse && s.spouse.eventsList.some(e => e.id == marriage.id)) return s.spouse;
    }
    return null;
}
