import * as React from "react";
import {Person} from "../../../protobuf/generated/person_pb";
import {nameToString} from "../../people/Name";

export const PersonCard = (props: {person: Person.AsObject}) => {

    const person = props.person;

    return <div className={"personCard " + genderClass(person.gender)}>

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