import * as React from "react";
import {FamilyTreeId} from "../tree/FamilyTree";
import {Person} from "../../protobuf/generated/person_pb";
import {personProfileHref} from "../../pages/people/PeopleLinks";
import {PersonName} from "../people/PersonName";

export const EventPersonBox = (props: {prefix: string, person: Person.AsObject, tree: FamilyTreeId}) => {
    if (!props.person) return null;
    return <div className="person">
        {props.prefix}
        {" "}
        <a href={personProfileHref(props.tree, props.person.id)}>
            <PersonName name={props.person.name}/>
        </a>
    </div>;
};