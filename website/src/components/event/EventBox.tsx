import * as React from "react";
import {Event} from "../../protobuf/generated/event_pb";
import {Card} from "antd";
import {renderAge} from "../people/Age";
import {eventType, findOtherEventPeople, isBirthEvent, isDeathEvent, isHistoricEvent, isPostDeathEvent} from "./Event";
import {EventTitle} from "./EventTitle";
import {Person} from "../../protobuf/generated/person_pb";
import {Family} from "../../protobuf/generated/family_pb";
import {FamilyTreeId} from "../tree/FamilyTree";
import {EventRelatedPeopleBox} from "./EventRelatedPeopleBox";
import {findEventSpouse, Relatives} from "../relationship/Relatives";
import {RelativeRelationship} from "../relationship/RelativeRelationship";
import {EventNotes} from "./EventNotes";
import {PlaceAndParent} from "../places/PlaceAndParent";
import {EventBoxDate} from "./EventBoxDate";

export type EventBoxProps = {
    person: Person.AsObject
    family: Family.AsObject
    tree: FamilyTreeId
    event: Event.AsObject
    relatives: Relatives
    relationshipToMain: RelativeRelationship
}

export const EventBox = (props: EventBoxProps) => {

    const person = props.person;
    if (!person) return null;

    const isSelf = props.relationshipToMain == "self";
    const event = props.event;

    return <Card className={"event " + eventClass(event)}>
        <div className="left">
            <EventBoxDate {...props}/>
        </div>
        <div className="right">
            <div className="top">
                <div className="title">
                    <EventTitle {...props} relationship={props.relationshipToMain}/>
                </div>
                {event.place && <div className="place">
                    <PlaceAndParent place={event.place}/>
                </div>}
                {event.computedage && isSelf && eventType(event) != "BIRTH" && <div className="computedAge">
                    Age {renderAge(event.computedage)}
                </div>}
            </div>
            <div className="main">
                {renderPeople(props)}
                <EventNotes event={event}/>
            </div>
        </div>
    </Card>;

};

function eventClass(event: Event.AsObject): string {
    if (isBirthEvent(event)) return "birth";
    if (isDeathEvent(event)) return "death";
    if (isPostDeathEvent(event)) return "postDeath";
    if (isHistoricEvent(event)) return "historic";
    return "postBirth";
}

function renderPeople(props: EventBoxProps) {
    if (!props.family || !props.relatives) return null;
    switch (eventType(props.event)) {
        case "BIRTH":
            return <EventRelatedPeopleBox {...props} people={[props.relatives.father, props.relatives.mother]}/>;
        case "MARRIAGE":
            return <EventRelatedPeopleBox {...props} people={[findEventSpouse(props.relatives, props.event)]}/>;
        default:
            return <FindRelatedPeopleBox {...props}/>;
    }
}

const FindRelatedPeopleBox = (props: EventBoxProps & {prefix?: React.ReactNode}) => {
    if (!props.relatives) return null;
    const others = findOtherEventPeople(props.person, props.family, props.event);
    return <EventRelatedPeopleBox {...props} people={others}/>;
};