import {Relationship} from "../../protobuf/generated/relationship_pb";
import {relationshipName} from "./Relationship";

test("relationshipName", () => {

    const r: Relationship.AsObject = {fromid: "from", toid: "to", type: Relationship.Type.CHILD_PARENT, unknown: false};
    expect(relationshipName(r)).toBe("Child");

});