import * as React from "react";
import {Event} from "../../protobuf/generated/event_pb";
import {Card, Icon} from "antd";
import {formatDateRange} from "../date/DateFormat";
import {PlaceLink} from "../places/PlaceLink";
import {renderAge} from "../people/Age";
import {eventType, isBirthEvent, isDeathEvent, isPostDeathEvent} from "./Event";
import {EventTitle} from "./EventTitle";
import {Person} from "../../protobuf/generated/person_pb";
import {Family} from "../../protobuf/generated/family_pb";
import {FamilyTreeId} from "../tree/FamilyTree";
import {EventRelatedPeopleBox} from "./EventRelatedPeopleBox";
import {Relatives} from "../relationship/Relatives";
import {RelativeRelationship} from "../relationship/RelativeRelationship";

export type EventBoxProps = {
    person: Person.AsObject
    family: Family.AsObject
    tree: FamilyTreeId
    event: Event.AsObject
    relatives: Relatives
    relationshipToMain: RelativeRelationship
}

export const EventBox = (props: EventBoxProps) => {

    if (!props.person) return null;

    const event = props.event;

    return <Card className={"event " + eventClass(event)}>
        <div className="top">
            <div className="title">
                <EventTitle {...props} relationship={props.relationshipToMain}/>
            </div>
            <div className="date">
                {formatDateRange(event.date, "day")}
            </div>
            {event.place && <div className="place">
                <PlaceLink place={event.place} showType/>
            </div>}
            {event.givenage && <div className="givenAge">
                Age {renderAge(event.givenage)}
            </div>}
        </div>
        <div className="main">
            {typeBox(props)}
        </div>
    </Card>;

};

function eventClass(event: Event.AsObject): string {
    if (isBirthEvent(event)) return "birth";
    if (isDeathEvent(event)) return "death";
    if (isPostDeathEvent(event)) return "postDeath";
    return "postBirth";
}

function typeBox(props: EventBoxProps) {
    if (!props.relatives) return null;
    switch (eventType(props.event)) {
        case "BIRTH":
            return <EventRelatedPeopleBox {...props} prefix="Parents" separator=" &amp; " people={[props.relatives.father, props.relatives.mother]}/>;
        case "MARRIAGE":
            return <RelatedPersonBox {...props} prefix={<><Icon type="swap"/> Spouse</>}/>;
        default:
            return null;
    }
}

const RelatedPersonBox = (props: EventBoxProps & {prefix: React.ReactNode}) => {
    if (!props.relatives) return null;
    const other = findOther(props.person, props.family, props.event);
    return <EventRelatedPeopleBox {...props} people={[other]}/>;
};

function findOther(person: Person.AsObject, family: Family.AsObject, event: Event.AsObject): Person.AsObject {
    if (event.personidList.length != 2) return null;
    const otherId = event.personidList.find(i => i != person.id);
    return otherId != null && family.personList.find(p => p.id == otherId);
}