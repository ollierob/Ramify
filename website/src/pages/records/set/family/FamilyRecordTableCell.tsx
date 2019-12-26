import * as React from "react";
import {FamilyRecord} from "./FamilyRecord";
import {List} from "antd";
import {Person} from "../../../../protobuf/generated/person_pb";
import {Family} from "../../../../protobuf/generated/family_pb";
import {Relationship} from "../../../../protobuf/generated/relationship_pb";
import {findRelationship, relationshipName} from "../../../../components/relationship/Relationship";
import {Name} from "../../../../protobuf/generated/name_pb";
import {sortPeopleByBirthDate} from "../../../../components/people/Person";
import {PersonName} from "../../../../components/people/PersonName";
import {renderLifespan} from "../../../../components/people/Lifespan";
import {moveToFront} from "../../../../components/Arrays";

export function renderFamilyRecord(record: FamilyRecord) {
    if (!record) return null;
    return renderFamily(record.family);
}

function renderFamily(family: Family.AsObject) {
    if (!family || !family.personList || !family.personList.length) return null;
    const list = [...family.personList].sort(sortPeopleByBirthDate);
    if (family.root) moveToFront(list, p => p.id == family.root.id);
    const root = list[0];
    return <List
        dataSource={list}
        renderItem={person => <List.Item key={person.id}>{renderPerson(person, root, family.relationshipList)}</List.Item>}/>;
}

function renderPerson(person: Person.AsObject, root: Person.AsObject, relationships: ReadonlyArray<Relationship.AsObject>) {
    return <>
        {renderName(person.name)}
        {renderLifespan(person)}
        {renderRelationship(person, root, relationships)}
        {renderNotes(person)}
    </>;
}

function renderName(name: Name.AsObject) {
    return <PersonName name={name}/>;
}

function renderRelationship(person: Person.AsObject, root: Person.AsObject, relationships: ReadonlyArray<Relationship.AsObject>) {
    const relationship = findRelationship(person, root, relationships);
    if (!relationship || !relationship.type) return null;
    return <>&nbsp;<span className="relationship">{relationshipName(relationship, person)}</span></>;
}

function renderNotes(person: Person.AsObject) {
    if (person.notes) return <span className="notes">{person.notes}</span>;
    const occupations = new Set(person.eventsList.map(e => e.occupation).filter(o => !!o));
    if (occupations.size == 1) return <span className="occupation">{Array.from(occupations)[0]}</span>;
    return null;
}