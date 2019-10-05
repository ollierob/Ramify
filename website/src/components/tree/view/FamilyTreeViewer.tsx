import * as React from "react";
import {Family} from "../../../protobuf/generated/family_pb";
import Draggable from "./Draggable";
import "./FamilyTreeViewer.css";
import {Person} from "../../../protobuf/generated/person_pb";
import {PersonCard} from "./PersonCard";
import {integerKeys, stringMap, StringMap} from "../../Maps";
import {rank} from "./FamilyTreeRanking";

type Props = {
    content?: boolean;
    family: Family.AsObject;
    zoom: number;
}

type PersonMap = StringMap<Person.AsObject>

type Ranking = {
    people: PersonMap
    ranks: {[rank: number]: string[]}
}

type State = Ranking;

export class FamilyTreeViewer extends React.PureComponent<Props, State> {

    constructor(props: Props) {
        super(props);
        this.state = generateRanking(props.family);
    }

    render() {

        return <div
            className={"treeViewer" + (this.props.content ? " content" : "")}>

            <Draggable>

                <div style={{transform: "scale(" + this.props.zoom + ")"}}>

                    {integerKeys(this.state.ranks)
                        .sort()
                        .map(k => <PersonRow line={this.state.ranks[k]} people={this.state.people}/>)}

                </div>
                
            </Draggable>

        </div>;

    }

    componentDidUpdate(prevProps: Readonly<Props>) {
        if (this.props.family != prevProps.family)
            this.setState(generateRanking(this.props.family));
    }

}

const PersonRow = (props: {people: PersonMap, line: string[]}) => {
    return <div className="row">
        {props.line.map(id => <PersonCard person={props.people[id]}/>)}
    </div>;
};

function generateRanking(family: Family.AsObject): Ranking {
    if (!family || !family.personList.length) return {people: {}, ranks: {}};
    return {
        people: stringMap(family.personList, p => p.id),
        ranks: rank(family.relationshipList)
    };
}