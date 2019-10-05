import * as React from "react";
import {Person} from "../../../protobuf/generated/person_pb";
import {nameToString} from "../../people/Name";
import {HasClass} from "../../style/HasClass";

export const PersonCard = (props: {person: Person.AsObject} & HasClass) => {

    const person = props.person;

    return <div
        style={props.style}
        className={"personCard " + genderClass(person.gender) + " " + (props.className || "")}>

        <div className="name">
            {nameToString(person.name)}
        </div>

    </div>;

};

function genderClass(gender: string): string {
    switch (gender.toLowerCase()) {
        case "male":
        case "female":
            return gender;
        default:
            return "unknown";
    }
}