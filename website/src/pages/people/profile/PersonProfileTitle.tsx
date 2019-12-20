import * as React from "react";
import {Person} from "../../../protobuf/generated/person_pb";
import {PersonName} from "../../../components/people/PersonName";
import {renderLifespan} from "../../../components/people/Lifespan";

export const PersonProfileTitle = (props: {person: Person.AsObject}) => {
    const person = props.person;
    return <><PersonName name={person.name}/> ({renderLifespan(person, {life: "", born: "b.", died: "d."})})</>;
};