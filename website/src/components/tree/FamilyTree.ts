import {FamilyTree} from "../../protobuf/generated/family_pb";
import {PersonId} from "../people/PersonId";
import {Person} from "../../protobuf/generated/person_pb";

export type FamilyTreeId = string;

export function findPersonInTree(id: PersonId, tree: FamilyTree.AsObject): Person.AsObject {
    if (!id || !tree || !tree.familyList) return null;
    for (const family of tree.familyList) {
        for (const person of family.personList) {
            if (person.id == id) return person;
        }
    }
    return null;
}