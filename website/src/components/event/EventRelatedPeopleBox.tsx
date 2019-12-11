import * as React from "react";
import {FamilyTreeId} from "../tree/FamilyTree";
import {Person} from "../../protobuf/generated/person_pb";
import {EventRelatedPerson} from "./EventRelatedPerson";

export const EventRelatedPeopleBox = (props: {prefix?: React.ReactNode, tree: FamilyTreeId, people: ReadonlyArray<Person.AsObject>}) => {

    if (!props.people || !props.people.length) return null;

    return <div className="related bordered">
        {props.people.map(p => <EventRelatedPerson key={p.id} {...props} person={p}/>)}
    </div>;

};