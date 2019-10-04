import {FamilyTreeId} from "./FamilyTree";
import {FamilyTree} from "../../protobuf/generated/family_pb";
import {protoGet} from "../fetch/ProtoFetch";

export interface FamilyTreeLoader {

    loadFamilyTree(id: FamilyTreeId): Promise<FamilyTree.AsObject>

}

class ProtoFamilyTreeLoader implements FamilyTreeLoader {

    loadFamilyTree(id: string): Promise<FamilyTree.AsObject> {
        return protoGet("/family/tree/load/" + id, FamilyTree.deserializeBinary)
            .then(t => t ? t.toObject() : null);
    }

}

export const DEFAULT_FAMILY_TREE_LOADER: FamilyTreeLoader = new ProtoFamilyTreeLoader();