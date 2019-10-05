import * as React from "react";
import {Person, Name} from "../../../protobuf/generated/person_pb";
import {nameToString} from "../../people/Name";
import {HasClass} from "../../style/HasClass";
import {birthYear, deathYear} from "../../event/Event";

type Props = {person: Person.AsObject} & HasClass
type State = {
    birthYear: number;
    deathYear: number;
    flourishedYear: number;
}

export class PersonCard extends React.PureComponent<Props, State> {

    constructor(props: Props) {
        super(props);
        this.state = computeState(props.person);
    }

    render() {

        const person = this.props.person;

        return <div
            style={this.props.style}
            className={"personCard " + genderClass(person.gender) + " " + (this.props.className || "")}>

            <PersonName name={person.name}/>
            <PersonDates {...this.state}/>

        </div>;

    }

    componentDidUpdate(prevProps: Readonly<Props>) {
        if (this.props.person != prevProps.person)
            this.setState(computeState(this.props.person));
    }

}

const PersonName = (props: {name: Name.AsObject}) => {
    return <div className="name">
        {nameToString(props.name)}
    </div>;
};

const PersonDates = (props: {birthYear: number, deathYear: number, flourishedYear: number}) => {
    return <div className="dates">
        {(props.birthYear || props.deathYear) && <>{props.birthYear} - {props.deathYear}</>}
        {!props.birthYear && !props.deathYear && !props.flourishedYear && <span className="unimportant">Unknown dates</span>}
    </div>;
};

function computeState(person: Person.AsObject): State {
    return {
        birthYear: person ? birthYear(person.eventsList) : null,
        deathYear: person ? deathYear(person.eventsList) : null,
        flourishedYear: null
    };
}

function genderClass(gender: string): string {
    switch (gender.toLowerCase()) {
        case "male":
        case "female":
            return gender;
        default:
            return "unknown";
    }
}
