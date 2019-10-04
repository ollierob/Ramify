import {FamilyTreeId} from "./FamilyTree";
import {FamilyTree, FamilyTreeList} from "../../protobuf/generated/family_pb";
import {protoGet} from "../fetch/ProtoFetch";

export interface FamilyTreeLoader {

    loadFamilyTrees(): Promise<ReadonlyArray<FamilyTree.AsObject>>

    loadFamilyTree(id: FamilyTreeId): Promise<FamilyTree.AsObject>

}

class ProtoFamilyTreeLoader implements FamilyTreeLoader {

    loadFamilyTrees(): Promise<ReadonlyArray<FamilyTree.AsObject>> {
        return protoGet("/people/families/trees/meta", FamilyTreeList.deserializeBinary)
            .then(l => l ? l.getFamilytreeList().map(t => t.toObject()) : []);
    }

    loadFamilyTree(id: string): Promise<FamilyTree.AsObject> {
        return protoGet("/people/families/trees/load/" + id, FamilyTree.deserializeBinary)
            .then(t => t ? t.toObject() : null);
    }

}

export const DEFAULT_FAMILY_TREE_LOADER: FamilyTreeLoader = new ProtoFamilyTreeLoader();