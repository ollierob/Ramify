import * as React from "react";
import {Person} from "../../protobuf/generated/person_pb";
import {FamilyTreeId} from "../tree/FamilyTree";
import {EmptyLifespanWords, LifespanWords, renderLifespan} from "../people/Lifespan";
import {personProfileHref} from "../../pages/people/PeopleLinks";
import {PersonName} from "../people/PersonName";

const Words: LifespanWords = {...EmptyLifespanWords, born: "born ", died: "died "};

export const EventRelatedPerson = (props: {prefix?: React.ReactNode, person: Person.AsObject, tree: FamilyTreeId}) => {

    if (!props.person) return null;

    const lifespan = renderLifespan(props.person, Words);
    return <div className="person">
        {props.prefix}
        {" "}
        <a href={personProfileHref(props.tree, props.person.id)}>
            <PersonName name={props.person.name}/>
        </a>
        {lifespan && <span className="life">({lifespan})</span>}
    </div>;

};
