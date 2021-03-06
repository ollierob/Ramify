import {Relationship} from "../../protobuf/generated/relationship_pb";
import {Person} from "../../protobuf/generated/person_pb";
import {isFemale, isMale} from "../people/Gender";
import {PersonId} from "../people/PersonId";
import {Family} from "../../protobuf/generated/family_pb";

export type RelationshipType = keyof Relationship.TypeMap;

const RelationshipTypeIndexes = relationshipTypeIndexes();

function relationshipTypeIndexes(): {[k: number]: RelationshipType} {
    const m: {[k: number]: RelationshipType} = {};
    Object.keys(Relationship.Type).map(k => k as keyof Relationship.TypeMap).forEach(k => m[Relationship.Type[k]] = k);
    return m;
}

const RelationshipNames: { [key in RelationshipType]: string } = {
    UNKNOWN: "Unknown",
    SPOUSE: "Spouse",
    SIBLING: "Sibling",
    PARENT_CHILD: "Parent",
    CHILD_PARENT: "Child",
    INDIRECT: "Indirect",
    FICTIVE: "Fictive"
};

export function relationshipType(relationship: Relationship.AsObject): RelationshipType {
    return RelationshipTypeIndexes[relationship.type];
}

export function relationshipName(relationship: Relationship.AsObject, person?: Person.AsObject): string {
    if (person) {
        switch (relationshipType(relationship)) {
            case "SPOUSE":
                if (isMale(person)) return "Husband";
                if (isFemale(person)) return "Wife";
                break;
            case "PARENT_CHILD":
                if (isMale(person)) return "Father";
                if (isFemale(person)) return "Mother";
                break;
            case "CHILD_PARENT":
                if (isMale(person)) return "Son";
                if (isFemale(person)) return "Daughter";
                break;
        }
    }
    return RelationshipNames[RelationshipTypeIndexes[relationship.type]];
}

export function isSpouse(relationship: Relationship.AsObject): boolean {
    return relationship.type == Relationship.Type.SPOUSE;
}

export function isSibling(relationship: Relationship.AsObject): boolean {
    return relationship.type == Relationship.Type.SIBLING;
}

export function isSpouseOrSibling(relationship: Relationship.AsObject): boolean {
    return isSpouse(relationship) || isSibling(relationship);
}

export function isParentChild(relationship: Relationship.AsObject): boolean {
    return relationship.type == Relationship.Type.PARENT_CHILD;
}

export function isChildParent(relationship: Relationship.AsObject): boolean {
    return relationship.type == Relationship.Type.CHILD_PARENT;
}

export function findRelationship(from: Person.AsObject, to: Person.AsObject, relationships: ReadonlyArray<Relationship.AsObject>, tryReverse: boolean = true): Relationship.AsObject {
    if (from == to) return null;
    const relationship = relationships.find(r => r.fromid == from.id && r.toid == to.id);
    if (!relationship) return tryReverse ? invertRelationship(findRelationship(to, from, relationships, false)) : null;
    return relationship;
}

export function invertRelationship(relationship: Relationship.AsObject): Relationship.AsObject {
    if (!relationship) return null;
    return {
        fromid: relationship.toid,
        toid: relationship.fromid,
        unknown: relationship.unknown,
        type: invertRelationshipType(relationship)
    };
}

function invertRelationshipType(relationship: Relationship.AsObject) {
    switch (relationship.type) {
        case Relationship.Type.PARENT_CHILD:
            return Relationship.Type.CHILD_PARENT;
        case Relationship.Type.CHILD_PARENT:
            return Relationship.Type.PARENT_CHILD;
        default:
            return relationship.type;
    }
}

export function findPerson(id: PersonId, family: Family.AsObject): Person.AsObject {
    return family.personList.find(p => p.id == id);
}