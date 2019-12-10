import * as React from "react";
import {Person} from "../../../protobuf/generated/person_pb";
import {EventList} from "../../../components/event/EventList";
import {Card, Tag} from "antd";
import {Family} from "../../../protobuf/generated/family_pb";
import {Event} from "../../../protobuf/generated/event_pb";
import {PersonName} from "../../../components/people/PersonName";
import {FamilyTreeId} from "../../../components/tree/FamilyTree";
import {RelativesList} from "./RelativesList";

const {CheckableTag} = Tag;

type Props = {
    person: Person.AsObject;
    family: Family.AsObject;
    tree: FamilyTreeId;
    loading: boolean;
}

export class PersonProfile extends React.PureComponent<Props> {

    render() {

        return <div className="profile content">

            {this.props.person && <>

                <ProfileGallery/>

                <ProfileEvents {...this.props} events={this.props.person.eventsList}/>

                <ProfileRelatives {...this.props}/>

            </>}

        </div>;

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

const ProfileEvents = (props: {person: Person.AsObject, family: Family.AsObject, tree: FamilyTreeId, events: ReadonlyArray<Event.AsObject>}) => {

    const [state, setState] = React.useState<EventListOptions>(() => ({ownEvents: true}));

    return <Card
        title={props.person.name ? <PersonName name={props.person.name}/> : "Events"}
        className="eventList large">
        <EventListControls state={state} setState={setState}/>
        <EventList {...props} {...state}/>
    </Card>;

};

const ProfileRelatives = (props: Props) => {
    return <Card
        title="Relatives"
        className="relatives large">
        <RelativesList {...props}/>
    </Card>;
};

const EventListControls = (props: {state: EventListOptions, setState: (s: EventListOptions) => void}) => {

    const {state, setState} = props;

    return <div>

        <CheckableTag checked={state.ownEvents} onChange={c => setState({...state, ownEvents: c})}>
            Own events
        </CheckableTag>

        <CheckableTag checked={state.familyEvents}>
            Family events
        </CheckableTag>

        <CheckableTag checked={state.historicEvents}>
            Historic events
        </CheckableTag>

    </div>;

};