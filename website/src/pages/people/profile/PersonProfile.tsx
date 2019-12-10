import * as React from "react";
import {Person} from "../../../protobuf/generated/person_pb";
import {EventList} from "../../../components/event/EventList";
import {Card} from "antd";
import {Family} from "../../../protobuf/generated/family_pb";
import {Event} from "../../../protobuf/generated/event_pb";
import {PersonName} from "../../../components/people/PersonName";
import {FamilyTreeId} from "../../../components/tree/FamilyTree";

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

                <ProfileRelatives family={this.props.family}/>

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

const ProfileEvents = (props: {person: Person.AsObject, family: Family.AsObject, tree: FamilyTreeId, events: ReadonlyArray<Event.AsObject>}) => {
    return <Card
        title={props.person.name ? <PersonName name={props.person.name}/> : "Events"}
        className="eventList large">
        <EventList {...props}/>
    </Card>;
};

const ProfileRelatives = (props: {family: Family.AsObject}) => {
    return <Card
        title="Relatives"
        className="relatives large">

    </Card>;
};