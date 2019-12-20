import * as React from "react";
import {Event} from "../../protobuf/generated/event_pb";
import {Card} from "antd";
import {EmptyPrefixWords, formatDateRange, formatShortYearRange, formatYear, formatYearRange} from "../date/DateFormat";
import {renderAge} from "../people/Age";
import {eventType, findOtherEventPeople, isBirthEvent, isDeathEvent, isPostDeathEvent} from "./Event";
import {EventTitle} from "./EventTitle";
import {Person} from "../../protobuf/generated/person_pb";
import {Family} from "../../protobuf/generated/family_pb";
import {FamilyTreeId} from "../tree/FamilyTree";
import {EventRelatedPeopleBox} from "./EventRelatedPeopleBox";
import {findEventSpouse, Relatives} from "../relationship/Relatives";
import {RelativeRelationship} from "../relationship/RelativeRelationship";
import {EventNotes} from "./EventNotes";
import {PlaceAndParent} from "../places/PlaceAndParent";
import {DateRange} from "../../protobuf/generated/date_pb";
import {isExactRange} from "../date/DateRange";
import {MonthDayWordsFormatter} from "../date/DateFormatter";

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

    const isSelf = props.relationshipToMain == "self";
    const event = props.event;

    return <Card className={"event " + eventClass(event)}>
        <div className="left">
            {renderDate(event.date)}
        </div>
        <div className="right">
            <div className="top">
                <div className="title">
                    <EventTitle {...props} relationship={props.relationshipToMain}/>
                </div>
                {event.place && <div className="place">
                    <PlaceAndParent place={event.place}/>
                </div>}
                {event.computedage && isSelf && <div className="computedAge">
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

function renderDate(date: DateRange.AsObject) {
    return <>
        <div className="year">{formatYearRange(date, EmptyPrefixWords)}</div>
        {isExactRange(date) && <div className="date">{formatDateRange(date, MonthDayWordsFormatter, EmptyPrefixWords)}</div>}
    </>;
}