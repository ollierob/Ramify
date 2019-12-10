import * as React from "react";
import {Event} from "../../protobuf/generated/event_pb";
import {Card} from "antd";
import {formatDateRange} from "../date/DateFormat";
import {PlaceLink} from "../places/PlaceLink";
import {renderAge} from "../people/Age";
import {eventType, isBirthEvent, isDeathEvent, isPostDeathEvent} from "./Event";
import {EventTitle} from "./EventTitle";
import {Person} from "../../protobuf/generated/person_pb";
import {Family} from "../../protobuf/generated/family_pb";
import {FamilyTreeId} from "../tree/FamilyTree";
import {EventPersonBox} from "./EventPersonBox";

export type EventBoxProps = {
    person: Person.AsObject
    family: Family.AsObject
    tree: FamilyTreeId
    event: Event.AsObject
}

export const EventBox = (props: EventBoxProps) => {

    const event = props.event;

    return <Card className={"event " + eventClass(event)}>
        <div className="top">
            <div className="title">
                <EventTitle {...props}/>
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
    switch (eventType(props.event)) {
        case "MARRIAGE":
            return <OtherPersonBox {...props} prefix="Spouse"/>;
        default:
            return null;
    }
}

const OtherPersonBox = (props: EventBoxProps & {prefix: string}) => {
    const other = findOther(props.person, props.family, props.event);
    return <EventPersonBox {...props} person={other}/>;
};

function findOther(person: Person.AsObject, family: Family.AsObject, event: Event.AsObject): Person.AsObject {
    if (event.personidList.length != 2) return null;
    const otherId = event.personidList.find(i => i != person.id);
    return otherId != null && family.personList.find(p => p.id == otherId);
}