import * as React from "react";
import {FamilyRecord} from "./FamilyRecord";
import {List} from "antd";
import {Name, Person} from "../../../../protobuf/generated/person_pb";
import {nameToString} from "../../../../components/people/Name";
import {Event} from "../../../../protobuf/generated/event_pb";
import {FormattedYearRange} from "../../../../components/date/FormattedDateRange";
import {findBirth} from "../../../../components/event/Event";
import {Family} from "../../../../protobuf/generated/family_pb";
import {Relationship} from "../../../../protobuf/generated/relationship_pb";
import {findRelationship, relationshipName} from "../../../../components/relationship/Relationship";

export function renderFamilyRecord(record: FamilyRecord) {
    if (!record) return null;
    switch (record.type) {
        default:
            return renderFamily(record.family);
    }
}

function renderFamily(family: Family.AsObject) {
    if (!family || !family.personList || !family.personList.length) return null;
    return <List
        dataSource={family.personList}
        renderItem={person => <List.Item>{renderPerson(person, family.personList[0], family.relationshipList)}</List.Item>}/>;
}

function renderPerson(person: Person.AsObject, root: Person.AsObject, relationships: ReadonlyArray<Relationship.AsObject>) {
    return <>
        {renderName(person.name)}
        {renderBirth(findBirth(person.eventsList))}
        {renderRelationship(person, root, relationships)}
    </>;
}

function renderName(name: Name.AsObject) {
    return <span className="name">{nameToString(name)}</span>;
}

function renderBirth(event: Event.AsObject) {
    if (!event) return null;
    return <span className="birth">Born <FormattedYearRange date={event.date} words={{in: "in"}}/></span>;
}

function renderRelationship(person: Person.AsObject, root: Person.AsObject, relationships: ReadonlyArray<Relationship.AsObject>) {
    const relationship = findRelationship(person, root, relationships, true);
    if (!relationship || !relationship.type) return null;
    return <span className="relationship">{relationshipName(relationship, person)}</span>;
}