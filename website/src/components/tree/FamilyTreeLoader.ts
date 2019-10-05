import {FamilyTreeId} from "./FamilyTree";
import {FamilyTree, FamilyTreeList} from "../../protobuf/generated/family_pb";
import {protoGet} from "../fetch/ProtoFetch";
import {PersonId} from "../people/PersonId";

export interface FamilyTreeLoader {

    loadFamilyTrees(): Promise<ReadonlyArray<FamilyTree.AsObject>>

    loadFamilyTree(id: FamilyTreeId, personId?: PersonId): Promise<FamilyTree.AsObject>

    loadFamilyTreeMeta(id: FamilyTreeId): Promise<FamilyTree.AsObject>

}

class ProtoFamilyTreeLoader implements FamilyTreeLoader {

    loadFamilyTrees(): Promise<ReadonlyArray<FamilyTree.AsObject>> {
        return protoGet("/people/families/trees/meta", FamilyTreeList.deserializeBinary)
            .then(l => l ? l.getFamilytreeList().map(t => t.toObject()) : []);
    }

    loadFamilyTree(id: FamilyTreeId, personId: PersonId): Promise<FamilyTree.AsObject> {
        return protoGet("/people/families/trees/load/" + id + (personId ? "/" + personId : ""), FamilyTree.deserializeBinary)
            .then(t => t ? t.toObject() : null);
    }

    loadFamilyTreeMeta(id: string): Promise<FamilyTree.AsObject> {
        return protoGet("/people/families/trees/meta/" + id, FamilyTree.deserializeBinary)
            .then(t => t ? t.toObject() : null);
    }

}

export const DEFAULT_FAMILY_TREE_LOADER: FamilyTreeLoader = new ProtoFamilyTreeLoader();