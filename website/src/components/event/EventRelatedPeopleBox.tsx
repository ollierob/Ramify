import * as React from "react";
import {FamilyTreeId} from "../tree/FamilyTree";
import {Person} from "../../protobuf/generated/person_pb";
import {EventRelatedPerson} from "./EventRelatedPerson";

type Props = {
    prefix?: React.ReactNode
    separator?: React.ReactNode;
    tree: FamilyTreeId;
    people: ReadonlyArray<Person.AsObject>;
}

export const EventRelatedPeopleBox = (props: Props) => {

    let people = props.people;
    if (!people) return null;
    people = people.filter(p => p != null);
    if (!people.length) return null;

    return <div className="related bordered">
        {people.map(p => <EventRelatedPerson key={p.id} {...props} person={p}/>)}
    </div>;

};