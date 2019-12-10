import * as React from "react";
import {Person} from "../../../protobuf/generated/person_pb";
import {Family} from "../../../protobuf/generated/family_pb";
import {FamilyTreeId} from "../../../components/tree/FamilyTree";
import {findChildren, findFather, findMother} from "../../../components/relationship/Family";
import {personProfileHref} from "../PeopleLinks";
import {PersonName} from "../../../components/people/PersonName";

type Props = {
    person: Person.AsObject
    family: Family.AsObject
    tree: FamilyTreeId
}

type State = {
    father?: Person.AsObject
    mother?: Person.AsObject
    children?: ReadonlyArray<Person.AsObject>
}

export class RelativesList extends React.PureComponent<Props, State> {

    constructor(props) {
        super(props);
        this.state = {};
    }

    render() {

        if (!this.props.person || !this.props.family) return null;

        return <ul>
            {this.state.father && <li>Father <RenderPerson {...this.props} person={this.state.father}/></li>}
            {this.state.mother && <li>Mother <RenderPerson {...this.props} person={this.state.mother}/></li>}
            {this.state.children && this.state.children.map(child => <li key={child.id}>Child <RenderPerson {...this.props} person={child}/></li>)}
        </ul>;

    }

    componentDidMount() {
        this.updateRelatives();
    }

    componentDidUpdate(prevProps: Readonly<Props>) {
        if (this.props.person != prevProps.person || this.props.family != prevProps.family)
            this.updateRelatives();
    }

    private updateRelatives(person: Person.AsObject = this.props.person, family: Family.AsObject = this.props.family) {
        if (!person || !family) return;
        this.setState({
            father: findFather(person.id, family),
            mother: findMother(person.id, family),
            children: findChildren(person.id, family)
        });
    }

}

const RenderPerson = (props: {person: Person.AsObject, tree: FamilyTreeId}) => {
    return <a href={personProfileHref(props.tree, props.person.id)}>
        <PersonName name={props.person.name}/>
    </a>;
};