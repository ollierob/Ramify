import * as React from "react";
import {FamilyTreeId} from "../tree/FamilyTree";
import {Person} from "../../protobuf/generated/person_pb";
import {personProfileHref} from "../../pages/people/PeopleLinks";
import {PersonName} from "../people/PersonName";
import {EmptyLifespanWords, LifespanWords, renderLifespan} from "../people/Lifespan";

const Words: LifespanWords = {...EmptyLifespanWords, born: "Born ", died: "Died "};

export const EventPersonBox = (props: {prefix: string, person: Person.AsObject, tree: FamilyTreeId}) => {

    if (!props.person) return null;

    return <div className="person bordered">
        {props.prefix}
        {" "}
        <a href={personProfileHref(props.tree, props.person.id)}>
            <PersonName name={props.person.name}/>
        </a>
        {renderLifespan(props.person, Words)}
    </div>;

};