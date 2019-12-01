import * as React from "react";
import {FamilyRecord} from "./FamilyRecord";
import {List} from "antd";
import {Person} from "../../../../protobuf/generated/person_pb";
import {FormattedDateRange, FormattedYearRange} from "../../../../components/date/FormattedDateRange";
import {findBirth, findDeath} from "../../../../components/event/Event";
import {Family} from "../../../../protobuf/generated/family_pb";
import {Relationship} from "../../../../protobuf/generated/relationship_pb";
import {findRelationship, relationshipName} from "../../../../components/relationship/Relationship";
import {Name} from "../../../../protobuf/generated/name_pb";
import {sortPeopleByBirthDate} from "../../../../components/people/Person";
import {formatYearRange} from "../../../../components/date/DateRange";

export function renderFamilyRecord(record: FamilyRecord) {
    if (!record) return null;
    switch (record.type) {
        default:
            return renderFamily(record.family);
    }
}

function renderFamily(family: Family.AsObject) {
    if (!family || !family.personList || !family.personList.length) return null;
    const list = [...family.personList].filter(p => p.name && !p.name.unknown).sort(sortPeopleByBirthDate);
    return <List
        dataSource={list}
        renderItem={person => <List.Item key={person.id}>{renderPerson(person, family.personList[0], family.relationshipList)}</List.Item>}/>;
}

function renderPerson(person: Person.AsObject, root: Person.AsObject, relationships: ReadonlyArray<Relationship.AsObject>) {
    return <>
        {renderName(person.name)}
        {renderLifespan(person)}
        {renderRelationship(person, root, relationships)}
    </>;
}

function renderName(name: Name.AsObject) {
    return name && <span className="name">{name.value}</span>;
}

function renderLifespan(person: Person.AsObject) {
    const birth = findBirth(person.eventsList);
    const death = findDeath(person.eventsList);
    if (birth && death) return <span className="birth death">Alive {formatYearRange(birth.date, death.date)}</span>;
    if (birth) return <span className="birth">Born <FormattedYearRange date={birth.date}/></span>;
    if (death) return <span className="death">Died <FormattedYearRange date={death.date}/></span>;
    return null;
}

function renderRelationship(person: Person.AsObject, root: Person.AsObject, relationships: ReadonlyArray<Relationship.AsObject>) {
    const relationship = findRelationship(person, root, relationships, true);
    if (!relationship || !relationship.type) return null;
    return <span className="relationship">{relationshipName(relationship, person)}</span>;
}