import * as React from "react";
import {Family} from "../../../protobuf/generated/family_pb";
import Draggable from "./Draggable";
import "./FamilyTreeViewer.css";
import {Person} from "../../../protobuf/generated/person_pb";
import {PersonCard} from "./PersonCard";
import {integerKeys, stringMap, StringMap} from "../../Maps";
import {rank} from "./FamilyTreeRanking";
import {PersonId} from "../../people/PersonId";
import {FamilyTreeId} from "../FamilyTree";

type Props = {
    content?: boolean;
    family: Family.AsObject;
    zoom: number;
    treeId: FamilyTreeId;
}

type PersonMap = StringMap<Person.AsObject>

type Ranking = {
    people: PersonMap
    ranks: {[rank: number]: PersonId[]}
}

type State = Ranking & {
    dragging?: boolean;
};

export class FamilyTreeViewer extends React.PureComponent<Props, State> {

    private readonly onDrag = (dragging: boolean) => this.setState({dragging});

    constructor(props: Props) {
        super(props);
        this.state = generateRanking(props.family);
    }

    render() {

        return <div
            className={"treeViewer" + (this.props.content ? " content" : "")}>

            <Draggable onDrag={this.onDrag}>

                <div style={{transform: "scale(" + this.props.zoom + ")"}}>

                    {integerKeys(this.state.ranks).sort().map(k => <PersonRow
                        key={"row-" + k}
                        line={this.state.ranks[k]}
                        treeId={this.props.treeId}
                        people={this.state.people}
                        dragging={this.state.dragging}/>)}

                </div>

            </Draggable>

        </div>;

    }

    componentDidUpdate(prevProps: Readonly<Props>) {
        if (this.props.family != prevProps.family)
            this.setState(generateRanking(this.props.family));
    }

}

const PersonRow = (props: {treeId: FamilyTreeId, people: PersonMap, line: PersonId[], dragging: boolean}) => {
    return <div className="row">
        {props.line.map(id => <PersonCard key={id} treeId={props.treeId} person={props.people[id]} dragging={props.dragging}/>)}
    </div>;
};

function generateRanking(family: Family.AsObject): Ranking {
    if (!family || !family.personList.length) return {people: {}, ranks: {}};
    return {
        people: stringMap(family.personList, p => p.id),
        ranks: rank(family.relationshipList)
    };
}