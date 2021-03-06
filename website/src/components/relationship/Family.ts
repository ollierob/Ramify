import {PersonId} from "../people/PersonId";
import {Family} from "../../protobuf/generated/family_pb";
import {Person} from "../../protobuf/generated/person_pb";
import {isFemale, isMale} from "../people/Gender";
import {findPerson, isChildParent, isParentChild, isSpouse} from "./Relationship";
import {distinct} from "../Arrays";

export function findParents(id: PersonId, family: Family.AsObject): ReadonlyArray<Person.AsObject> {
    const parentChild = family.relationshipList.filter(isParentChild).filter(r => r.toid == id).map(r => r.fromid);
    const childParent = family.relationshipList.filter(isChildParent).filter(r => r.fromid == id).map(r => r.toid);
    return parentChild.concat(childParent).map(id => findPerson(id, family));
}

export function findFather(id: PersonId, family: Family.AsObject): Person.AsObject {
    return findParents(id, family).filter(isMale)[0] || null;
}

export function findMother(id: PersonId, family: Family.AsObject): Person.AsObject {
    return findParents(id, family).filter(isFemale)[0] || null;
}

export function findSpouses(id: PersonId, family: Family.AsObject): ReadonlyArray<Person.AsObject> {
    const spouses = family.relationshipList.filter(isSpouse).filter(r => r.fromid == id || r.toid == id);
    const ids = distinct(spouses.map(r => r.fromid == id ? r.toid : r.fromid));
    return ids.map(id => findPerson(id, family));
}

export function findChildren(person: PersonId, family: Family.AsObject): ReadonlyArray<Person.AsObject> {
    if (!person) return [];
    const parentChild = family.relationshipList.filter(isParentChild).filter(r => r.fromid == person).map(r => r.toid);
    const childParent = family.relationshipList.filter(isChildParent).filter(r => r.toid == person).map(r => r.fromid);
    return parentChild.concat(childParent).map(id => findPerson(id, family));
}

export function findSharedChildren(p1: PersonId, p2: PersonId, family: Family.AsObject): ReadonlyArray<Person.AsObject> {
    const p1Children = findChildren(p1, family);
    const p2Children = findChildren(p2, family);
    return p1Children.filter(c1 => p2Children.some(c2 => c2.id == c1.id));
}

export function findSiblings(id: PersonId, parents: ReadonlyArray<PersonId>, family: Family.AsObject): ReadonlyArray<Person.AsObject> {
    if (!id) return [];
    if (!parents) parents = findParents(id, family).map(p => p.id);
    if (!parents.length) return [];
    const siblings: {[id: string]: Person.AsObject} = {};
    for (const parent of parents) {
        findChildren(parent, family).forEach(child => siblings[child.id] = child);
    }
    delete siblings[id];
    return Object.values(siblings);
}