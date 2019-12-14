import * as React from "react";
import {FamilyTreeId} from "../tree/FamilyTree";
import {Person} from "../../protobuf/generated/person_pb";
import {EventRelatedPerson} from "./EventRelatedPerson";
import {determineRelationship} from "../relationship/RelativeRelationship";
import {Relatives} from "../relationship/Relatives";

type Props = {
    prefix?: React.ReactNode
    tree: FamilyTreeId;
    people: ReadonlyArray<Person.AsObject>;
    relatives: Relatives
}

export const EventRelatedPeopleBox = (props: Props) => {

    let people = props.people;
    if (!people) return null;
    people = people.filter(p => p != null);
    if (!people.length) return null;

    return <div className="related bordered">
        {people.map(p => <EventRelatedPerson key={p.id} {...props} person={p} relationship={determineRelationship(p.id, props.relatives)}/>)}
    </div>;

};