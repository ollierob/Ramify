import * as React from "react";
import {Event} from "../../protobuf/generated/event_pb";
import {PersonName} from "../people/PersonName";
import {Person} from "../../protobuf/generated/person_pb";
import {RelativeRelationship, renderRelationship} from "../relationship/RelativeRelationship";
import {FamilyTreeId} from "../tree/FamilyTree";
import {personProfileHref} from "../../pages/people/PeopleLinks";
import {Link} from "../style/Links";

type Props = {
    event: Event.AsObject
    person: Person.AsObject
    relationship?: RelativeRelationship
    tree: FamilyTreeId
}

export const EventTitle = (props: Props) => {
    return <div className="title">
        {props.event.title}
        {props.relationship && props.relationship != "self"
        && <> of {renderRelationship(props.relationship, props.person)} <a href={personProfileHref(props.tree, props.person.id)}><PersonName name={props.person.name}/></a></>}
        {props.event.linkList && props.event.linkList.map(link => <Link link={link} newWindow/>)}
    </div>;
};