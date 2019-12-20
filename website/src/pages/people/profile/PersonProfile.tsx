import * as React from "react";
import {Person} from "../../../protobuf/generated/person_pb";
import {EventList} from "../../../components/event/EventList";
import {Card, Tag} from "antd";
import {Family} from "../../../protobuf/generated/family_pb";
import {FamilyTreeId} from "../../../components/tree/FamilyTree";
import {RelativesList} from "./RelativesList";
import {determineRelatives, Relatives} from "../../../components/relationship/Relatives";
import {ProfileEvent, sortProfileEvents} from "../../../components/event/ProfileEvent";
import {Event} from "../../../protobuf/generated/event_pb";
import {eventType, findBirth, findDeath} from "../../../components/event/Event";
import {isDateOrdered} from "../../../components/date/DateRange";
import {RelativeRelationship} from "../../../components/relationship/RelativeRelationship";
import {PersonProfileTitle} from "./PersonProfileTitle";

const flatten = require('arr-flatten');
const {CheckableTag} = Tag;

type Props = {
    person: Person.AsObject;
    family: Family.AsObject;
    tree: FamilyTreeId;
    loading: boolean;
}

type State = {
    relatives: Readonly<Relatives>
    events: ReadonlyArray<ProfileEvent>
}

export class PersonProfile extends React.PureComponent<Props, State> {

    constructor(props: Props) {
        super(props);
        const relatives: Relatives = props.person && determineRelatives(props.person.id, props.family);
        this.state = {
            relatives: relatives,
            events: this.determineEvents(props.person, relatives)
        };
    }

    render() {

        const person = this.props.person;
        if (!person) return null;

        return <Card
            title={<PersonProfileTitle person={person}/>}
            className="profile large">

            {person && <>

                <ProfileEvents
                    {...this.props}
                    {...this.state}/>

                <ProfileRelatives
                    {...this.props}
                    {...this.state}/>

            </>}

        </Card>;

    }

    componentDidCatch(error: Error, errorInfo: React.ErrorInfo) {
        //TODO
        console.error(error);
    }

    componentDidUpdate(prevProps: Readonly<Props>) {
        if (this.props.person != prevProps.person || this.props.family != prevProps.family) {
            const relatives = this.props.person && determineRelatives(this.props.person.id, this.props.family);
            this.setState({relatives: relatives, events: this.determineEvents(this.props.person, relatives)});
        }
    }

    private determineEvents(person: Person.AsObject, relatives: Relatives): ReadonlyArray<ProfileEvent> {
        return this.determinePersonEvents(person).concat(this.determineFamilyEvents(person, relatives)).concat(this.determineHistoricEvents()).sort(sortProfileEvents);
    }

    private determinePersonEvents(person: Person.AsObject): ReadonlyArray<ProfileEvent> {
        if (!person) return [];
        return person.eventsList.map(event => ({event, person, type: "person", relationshipToMain: "self"}));
    }

    private determineFamilyEvents(person: Person.AsObject, relatives: Relatives): ReadonlyArray<ProfileEvent> {
        if (!person || !relatives) return [];
        const birth = findBirth(person.eventsList);
        const death = findDeath(person.eventsList);
        const events: ProfileEvent[] = [];
        events.push(...relativeEvents(relatives.father, "parent", person, birth, death));
        events.push(...relativeEvents(relatives.mother, "parent", person, birth, death));
        for (const s of relatives.spouses) {
            events.push(...relativeEvents(s.spouse, "spouse", person, birth, death));
            s.children.forEach(child => events.push(...relativeEvents(child, "child", person, birth, death)));
        }
        for (const s of relatives.siblings) {
            events.push(...relativeEvents(s, "sibling", person, birth, death));
        }
        return events;
    }

    private determineHistoricEvents(): ReadonlyArray<ProfileEvent> {
        return []; //TODO
    }

}

function relativeEvents(relative: Person.AsObject, relationship: RelativeRelationship, mainPerson: Person.AsObject, mainBirth: Event.AsObject, mainDeath: Event.AsObject): ReadonlyArray<ProfileEvent> {
    if (!relative) return [];
    const events: ProfileEvent[] = [];
    for (const event of relative.eventsList) {
        if (!retainFamilyEvent(event)) continue;
        if (!isDateOrdered(mainBirth?.date, event.date, mainDeath?.date)) continue;
        events.push({event, person: relative, type: "family", relationshipToMain: relationship});
    }
    return events;
}

function retainFamilyEvent(event: Event.AsObject): boolean {
    switch (eventType(event)) {
        case "BIRTH":
        case "DEATH":
            return true;
        default:
            return false;
    }
}

type EventListOptions = {
    ownEvents: boolean;
    familyEvents?: boolean;
    historicEvents?: boolean;
}

const ProfileEvents = (props: Props & {relatives: Relatives, events: ReadonlyArray<ProfileEvent>}) => {

    const [state, setState] = React.useState<EventListOptions>(() => ({ownEvents: true}));

    if (!props.person) return null;

    return <div className="events">

        <EventListControls
            state={state}
            setState={setState}/>

        <EventList
            {...props}
            {...state}
            events={props.events.filter(e => showEvent(e, state))}/>

    </div>;

};

function showEvent(event: ProfileEvent, options: EventListOptions): boolean {
    switch (event.type) {
        case "person":
            return options.ownEvents;
        case "family":
            return options.familyEvents;
        case "historic":
            return options.historicEvents;
    }
}

const ProfileRelatives = (props: Props & State) => {
    return <div className="relatives">
        <RelativesList {...props}/>
    </div>;
};

const EventListControls = (props: {state: EventListOptions, setState: (s: EventListOptions) => void}) => {

    const {state, setState} = props;

    return <div className="controls">

        <CheckableTag checked={state.ownEvents} onChange={c => setState({...state, ownEvents: c})}>
            Own events
        </CheckableTag>

        <CheckableTag checked={state.familyEvents} onChange={c => setState({...state, familyEvents: c})}>
            Family events
        </CheckableTag>

        <CheckableTag checked={state.historicEvents} onChange={c => setState({...state, historicEvents: c})}>
            Historic events
        </CheckableTag>

    </div>;

};