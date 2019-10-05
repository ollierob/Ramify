import * as React from "react";
import {FamilyTree} from "../../../protobuf/generated/family_pb";
import {AsyncData} from "../../fetch/AsyncData";
import Draggable from "./Draggable";
import "./FamilyTreeViewer.css";
import {Person} from "../../../protobuf/generated/person_pb";
import {PersonCard} from "./PersonCard";

type Props = {
    content?: boolean;
    tree: AsyncData<FamilyTree.AsObject>
}

export class FamilyTreeViewer extends React.PureComponent<Props> {

    render() {

        const people = allPeople(this.props.tree.data);

        return <div className={"treeViewer" + (this.props.content ? " content" : "")}>

            <Draggable>

                {people.map(person => <PersonCard person={person}/>)}

            </Draggable>

        </div>;

    }

}

function allPeople(tree: FamilyTree.AsObject): Person.AsObject[] {
    const people: Person.AsObject[] = [];
    if (!tree) return people;
    tree.familyList.forEach(family => people.push(...family.personList));
    return people;
}