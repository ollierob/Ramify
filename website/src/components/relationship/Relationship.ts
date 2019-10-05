import {Relationship} from "../../protobuf/generated/relationship_pb";

export function invertRelationship(relationship: Relationship.AsObject): Relationship.AsObject {
    if (isHorizontal(relationship)) return relationship;
    return {
        fromid: relationship.toid,
        toid: relationship.fromid,
        unknown: relationship.unknown,
        direction: isParentChild(relationship) ? 3 : 2
    };
}

export function isHorizontal(relationship: Relationship.AsObject): boolean {
    return relationship.direction == 0 || relationship.direction == 1;
}

export function isParentChild(relationship: Relationship.AsObject): boolean {
    return relationship.direction == 2;
}