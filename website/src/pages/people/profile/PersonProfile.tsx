import * as React from "react";
import {Person} from "../../../protobuf/generated/person_pb";
import {EventList} from "../../../components/event/EventList";
import {Card, Tag} from "antd";
import {Family} from "../../../protobuf/generated/family_pb";
import {Event} from "../../../protobuf/generated/event_pb";
import {PersonName} from "../../../components/people/PersonName";
import {FamilyTreeId} from "../../../components/tree/FamilyTree";
import {RelativesList} from "./RelativesList";
import {determineRelatives, Relatives} from "../../../components/relationship/Relatives";

const {CheckableTag} = Tag;

type Props = {
    person: Person.AsObject;
    family: Family.AsObject;
    tree: FamilyTreeId;
    loading: boolean;
}

type State = {
    relatives: Readonly<Relatives>
}

export class PersonProfile extends React.PureComponent<Props, State> {

    constructor(props: Props) {
        super(props);
        this.state = {
            relatives: props.person && determineRelatives(props.person.id, props.family)
        };
    }

    render() {

        return <div className="profile content">

            {this.props.person && <>

                <ProfileGallery/>

                <ProfileEvents {...this.props} events={this.props.person.eventsList} relatives={this.state.relatives}/>

                <ProfileRelatives {...this.props} {...this.state}/>

            </>}

        </div>;

    }

    componentDidUpdate(prevProps: Readonly<Props>) {
        if (this.props.person != prevProps.person || this.props.family != prevProps.family)
            this.setState({relatives: this.props.person && determineRelatives(this.props.person.id, this.props.family)});
    }

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

const ProfileEvents = (props: Props & {events: ReadonlyArray<Event.AsObject>, relatives: Relatives}) => {

    const [state, setState] = React.useState<EventListOptions>(() => ({ownEvents: true}));

    if (!props.person) return null;

    return <Card
        title={<PersonName name={props.person.name}/> || "Events"}
        className="eventList large">
        <EventListControls state={state} setState={setState}/>
        <EventList {...props} {...state}/>
    </Card>;

};

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