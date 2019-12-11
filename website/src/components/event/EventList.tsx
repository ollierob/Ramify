import * as React from "react";
import {Event} from "../../protobuf/generated/event_pb";
import {EventBox} from "./EventBox";
import {Person} from "../../protobuf/generated/person_pb";
import {Family} from "../../protobuf/generated/family_pb";
import {FamilyTreeId} from "../tree/FamilyTree";
import "./EventList.css";
import {Relatives} from "../relationship/Relatives";

type Props = {
    person: Person.AsObject;
    family: Family.AsObject;
    tree: FamilyTreeId;
    events: ReadonlyArray<Event.AsObject>;
    relatives: Relatives
}

export class EventList extends React.PureComponent<Props> {

    render() {

        const events = this.props.events || [];

        //FIXME add event ID as key
        return <>
            {events.map(e => <EventBox {...this.props} event={e}/>)}
        </>;

    }

}