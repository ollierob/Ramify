import * as React from "react";
import {Person} from "../../../protobuf/generated/person_pb";
import {FamilyTreeId} from "../../../components/tree/FamilyTree";
import {personProfileHref} from "../PeopleLinks";
import {PersonName} from "../../../components/people/PersonName";
import {Relatives} from "../../../components/relationship/Relatives";

type Props = {
    person: Person.AsObject
    tree: FamilyTreeId
    relatives: Relatives
}

export class PersonProfileRelativesList extends React.PureComponent<Props> {

    constructor(props) {
        super(props);
        this.state = {};
    }

    render() {

        if (!this.props.person || !this.props.relatives) return null;
        const relatives = this.props.relatives;

        return <ul className="relatives">
            {relatives.father && <li>Father <RenderPerson {...this.props} person={relatives.father}/></li>}
            {relatives.mother && <li>Mother <RenderPerson {...this.props} person={relatives.mother}/></li>}
        </ul>;

    }

}

const RenderPerson = (props: {person: Person.AsObject, tree: FamilyTreeId}) => {
    return <a href={personProfileHref(props.tree, props.person.id)}>
        <PersonName name={props.person.name}/>
    </a>;
};