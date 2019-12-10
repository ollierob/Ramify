import {PersonId} from "../people/PersonId";
import {Family} from "../../protobuf/generated/family_pb";
import {Person} from "../../protobuf/generated/person_pb";
import {isFemale, isMale} from "../people/Gender";
import {findPerson, isChildParent, isParentChild} from "./Relationship";

export function findParents(person: PersonId, family: Family.AsObject): ReadonlyArray<Person.AsObject> {
    const parentChild = family.relationshipList.filter(isParentChild).filter(r => r.toid == person).map(r => r.fromid);
    const childParent = family.relationshipList.filter(isChildParent).filter(r => r.fromid == person).map(r => r.toid);
    return parentChild.concat(childParent).map(id => findPerson(id, family));
}

export function findFather(person: PersonId, family: Family.AsObject): Person.AsObject {
    return findParents(person, family).filter(isMale)[0] || null;
}

export function findMother(person: PersonId, family: Family.AsObject): Person.AsObject {
    return findParents(person, family).filter(isFemale)[0] || null;
}

export function findChildren(person: PersonId, family: Family.AsObject): ReadonlyArray<Person.AsObject> {
    const parentChild = family.relationshipList.filter(isParentChild).filter(r => r.fromid == person).map(r => r.toid);
    const childParent = family.relationshipList.filter(isChildParent).filter(r => r.toid == person).map(r => r.fromid);
    return parentChild.concat(childParent).map(id => findPerson(id, family));
}