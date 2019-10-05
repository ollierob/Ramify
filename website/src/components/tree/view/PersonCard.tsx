import * as React from "react";
import {Person, Name} from "../../../protobuf/generated/person_pb";
import {nameToString} from "../../people/Name";
import {HasClass} from "../../style/HasClass";
import {birthYear, deathYear} from "../../event/Event";
import {Icon, Popover} from "antd";

type Props = HasClass & {
    person: Person.AsObject
    dragging?: boolean
}

type State = {
    birthYear: number;
    deathYear: number;
    flourishedYear: number;
    popoverVisible?: boolean;
}

export class PersonCard extends React.PureComponent<Props, State> {

    private readonly setPopoverVisible = (visible: boolean) => this.setState({popoverVisible: visible});

    constructor(props: Props) {
        super(props);
        this.state = this.calculateState(props.person);
    }

    render() {

        const person = this.props.person;

        return <Popover
            placement="rightTop"
            trigger="click"
            visible={this.state.popoverVisible}
            onVisibleChange={this.setPopoverVisible}
            content={<PersonControls/>}>

            <div
                style={this.props.style}
                className={"personCard " + this.genderClass(person.gender) + " " + (this.props.className || "")}>

                <PersonName name={person.name}/>
                <PersonDates {...this.state}/>

            </div>

        </Popover>;

    }

    componentDidUpdate(prevProps: Readonly<Props>) {
        if (this.props.person != prevProps.person)
            this.setState(this.calculateState(this.props.person));
        if (this.props.dragging && this.state.popoverVisible)
            this.setState({popoverVisible: false});
    }

    private calculateState(person: Person.AsObject): State {
        return {
            birthYear: person ? birthYear(person.eventsList) : null,
            deathYear: person ? deathYear(person.eventsList) : null,
            flourishedYear: null
        };
    }

    private genderClass(gender: string): string {
        switch (gender.toLowerCase()) {
            case "male":
            case "female":
                return gender;
            default:
                return "unknown";
        }
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


const PersonControls = (props: {}) => {
    return <>
        <div>
            <Icon type="profile"/> View profile
        </div>
        <div>
            <Icon type="search"/> Search records
        </div>
        <div>
            <Icon type="swap"/> Merge
        </div>
        <div>
            <Icon type="delete"/> Delete
        </div>
    </>;
};