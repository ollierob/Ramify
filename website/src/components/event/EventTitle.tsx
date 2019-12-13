import * as React from "react";
import {EventBoxProps} from "./EventBox";
import {Event} from "../../protobuf/generated/event_pb";
import {PersonName} from "../people/PersonName";
import {Person} from "../../protobuf/generated/person_pb";
import {RelativeRelationship} from "../relationship/RelativeRelationship";
import {isFemale, isMale} from "../people/Gender";

type Props = {
    event: Event.AsObject
    person: Person.AsObject
    relationship?: RelativeRelationship
}

export const EventTitle = (props: Props) => {
    return <>
        {props.event.title}
        {props.relationship && props.relationship != "self" && <> of {renderRelationship(props.relationship, props.person)} <PersonName name={props.person.name}/></>}
    </>;
};

function renderRelationship(relationship: RelativeRelationship, person: Person.AsObject) {
    switch (relationship) {
        case "parent":
            if (isMale(person)) return "father";
            if (isFemale(person)) return "mother";
            return "parent";
        case "spouse":
            if (isMale(person)) return "husband";
            if (isFemale(person)) return "wife";
            return "spouse";
        case "child":
            if (isMale(person)) return "son";
            if (isFemale(person)) return "daughter";
            return "child";
        default:
            return null;
    }
}