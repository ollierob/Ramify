import {Relationship} from "../../../protobuf/generated/relationship_pb";
import {ensure, StringMap} from "../../Maps";
import {isHorizontal, isParentChild} from "../../relationship/Relationship";
import {PersonId} from "../../people/PersonId";

type Ranking = {[rank: number]: PersonId[]}
type TreeNode = {id: PersonId, parents: TreeNode[], children: TreeNode[], siblings: TreeNode[], ancestors?: number, generation?: number};

export function rank(relationships: ReadonlyArray<Relationship.AsObject>): Ranking {

    if (!relationships || !relationships.length) return {};

    const nodes: StringMap<TreeNode> = {};

    for (const relationship of relationships) {

        const n1 = ensure(relationship.fromid, nodes, createNode);
        const n2 = ensure(relationship.toid, nodes, createNode);
        joinNodes(n1, n2, relationship);

    }

    const allNodes = Object.values(nodes);
    allNodes.forEach(countAncestors);
    allNodes.forEach(computeGeneration);
    return computeDepths(allNodes);

}

function createNode(id: PersonId): TreeNode {
    return {id, parents: [], children: [], siblings: []};
}

function joinNodes(n1: TreeNode, n2: TreeNode, relationship: Relationship.AsObject) {
    if (isHorizontal(relationship)) {
        n1.siblings.push(n2);
        n2.siblings.push(n1);
    } else if (isParentChild(relationship)) {
        n1.children.push(n2);
        n2.parents.push(n1);
    } else {
        n1.parents.push(n2);
        n2.children.push(n1);
    }
}

function computeDepths(nodes: TreeNode[]): Ranking {
    //FIXME this needs to be multi-pass, should compute "generation"
    const ranking: Ranking = {};
    nodes.forEach(node => {
        const rank = node.generation || 0;
        let atRank = ranking[rank];
        if (atRank == null) atRank = ranking[rank] = [];
        atRank.push(node.id);
    });
    return ranking;
}

function countAncestors(node: TreeNode): number {
    if (!node.parents.length) node.ancestors = 0;
    if (node.ancestors != null) return node.ancestors;
    const count = 1 + Math.max(...node.parents.map(countAncestors));
    node.ancestors = count;
    return count;
}

function computeGeneration(node: TreeNode) {
    node.generation = node.ancestors;
    if (!node.siblings.length) return;
    const maxAncestors = Math.max(...node.siblings.map(s => s.generation || s.ancestors));
    if (maxAncestors > node.generation) node.generation = maxAncestors;
}