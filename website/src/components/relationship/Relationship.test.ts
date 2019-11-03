import {Relationship} from "../../protobuf/generated/relationship_pb";
import {invertRelationship, relationshipName} from "./Relationship";

test("relationshipName", () => {

    const r: Relationship.AsObject = {fromid: "from", toid: "to", type: Relationship.Type.CHILD_PARENT, unknown: false};
    expect(relationshipName(r)).toBe("Child");

});

test("invertRelationship", () => {

    const r: Relationship.AsObject = {fromid: "parent", toid: "child", type: Relationship.Type.PARENT_CHILD, unknown: false};
    expect(invertRelationship(r)).toEqual({
        fromid: "child",
        toid: "parent",
        type: Relationship.Type.CHILD_PARENT,
        unknown: false,
    });

});