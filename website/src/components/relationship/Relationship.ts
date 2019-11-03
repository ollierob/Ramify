import {Relationship} from "../../protobuf/generated/relationship_pb";
import {Person} from "../../protobuf/generated/person_pb";

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
    CHILD_PARENT: "Child"
};

export function relationshipType(relationship: Relationship.AsObject): RelationshipType {
    return RelationshipTypeIndexes[relationship.type];
}

export function relationshipName(relationship: Relationship.AsObject): string {
    return RelationshipNames[RelationshipTypeIndexes[relationship.type]];
}

export function isSpouseOrSibling(relationship: Relationship.AsObject): boolean {
    switch (relationship.type) {
        case Relationship.Type.SPOUSE:
        case Relationship.Type.SIBLING:
            return true;
        default:
            return false;
    }
}

export function isParentChild(relationship: Relationship.AsObject): boolean {
    return relationship.type == Relationship.Type.PARENT_CHILD;
}

export function determineRelationship(from: Person.AsObject, to: Person.AsObject, relationships: ReadonlyArray<Relationship.AsObject>, tryReverse?: boolean): RelationshipType {
    if (from == to) return null;
    const relationship = relationships.find(r => r.fromid == from.id && r.toid == to.id);
    if (!relationship) return tryReverse ? determineRelationship(to, from, relationships, false) : null;
    return relationshipType(relationship);
}