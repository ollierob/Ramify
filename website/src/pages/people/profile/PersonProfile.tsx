import * as React from "react";
import {Person} from "../../../protobuf/generated/person_pb";
import {EventList} from "../../../components/event/EventList";
import {Card, Tag} from "antd";
import {Family} from "../../../protobuf/generated/family_pb";
import {PersonName} from "../../../components/people/PersonName";
import {FamilyTreeId} from "../../../components/tree/FamilyTree";
import {RelativesList} from "./RelativesList";
import {allRelatives, determineRelatives, Relatives} from "../../../components/relationship/Relatives";
import {ProfileEvent, sortProfileEvents} from "../../../components/event/ProfileEvent";
import {Event} from "../../../protobuf/generated/event_pb";
import {eventType} from "../../../components/event/Event";

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

        return <div className="profile content">

            {this.props.person && <>

                <ProfileGallery/>

                <ProfileEvents
                    {...this.props}
                    {...this.state}/>

                <ProfileRelatives
                    {...this.props}
                    {...this.state}/>

            </>}

        </div>;

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
        return person.eventsList.map(event => ({event, type: "person"}));
    }

    private determineFamilyEvents(person: Person.AsObject, relatives: Relatives): ReadonlyArray<ProfileEvent> {
        if (!person || !relatives) return [];
        const events: Event.AsObject[] = flatten(allRelatives(relatives).map(r => r.eventsList));
        return events.filter(retainFamilyEvent).filter(e => !e.personidList.includes(person.id)).map<ProfileEvent>(event => ({event, type: "family"}));
    }

    private determineHistoricEvents(): ReadonlyArray<ProfileEvent> {
        return []; //TODO
    }

}

function retainFamilyEvent(event: Event.AsObject): boolean {
    return event.isunique;
}

const ProfileGallery = () => {
    return <Card
        title="Gallery"
        className="gallery large">

    </Card>;
};

type EventListOptions = {
    ownEvents: boolean;
    familyEvents?: boolean;
    historicEvents?: boolean;
}

const ProfileEvents = (props: Props & {relatives: Relatives, events: ReadonlyArray<ProfileEvent>}) => {

    const [state, setState] = React.useState<EventListOptions>(() => ({ownEvents: true}));

    if (!props.person) return null;

    return <Card
        title={<PersonName name={props.person.name}/> || "Events"}
        className="eventList large">

        <EventListControls
            state={state}
            setState={setState}/>

        <EventList
            {...props}
            {...state}
            events={props.events.filter(e => showEvent(e, state))}/>

    </Card>;

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
    return <Card
        title="Relatives"
        className="relatives large">
        <RelativesList {...props}/>
    </Card>;
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