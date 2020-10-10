import * as React from "react";
import {Person} from "../../../protobuf/generated/person_pb";
import {Card, Tabs} from "antd";
import {Family} from "../../../protobuf/generated/family_pb";
import {FamilyTreeId} from "../../../components/tree/FamilyTree";
import {determineRelatives, Relatives} from "../../../components/relationship/Relatives";
import {ProfileEvent} from "../../../components/event/ProfileEvent";
import {Event} from "../../../protobuf/generated/event_pb";
import {eventType} from "../../../components/event/Event";
import {isDateOrdered} from "../../../components/date/DateRange";
import {RelativeRelationship} from "../../../components/relationship/RelativeRelationship";
import {PersonProfileTitle} from "./PersonProfileTitle";
import {PersonProfileEvents} from "./PersonProfileEvents";
import {lifeDateRange} from "../../../components/people/Person";
import {DateRange} from "../../../protobuf/generated/date_pb";
import PersonProfileSources from "./PersonProfileSources";

type Props = {
    person: Person.AsObject;
    family: Family.AsObject;
    tree: FamilyTreeId;
    loading: boolean;
}

type State = {
    relatives: Readonly<Relatives>
    events: ReadonlyArray<ProfileEvent>
    selectedEvent: ProfileEvent
}

export class PersonProfileCard extends React.PureComponent<Props, State> {

    private readonly setSelectedEvent = (selectedEvent: ProfileEvent) => this.setState({selectedEvent});

    constructor(props: Props) {
        super(props);
        const relatives: Relatives = props.person && determineRelatives(props.person.id, props.family);
        this.state = {
            relatives,
            events: this.determineEvents(props.person, relatives),
            selectedEvent: null
        };
    }

    render() {

        const person = this.props.person;
        if (!person) return null;

        return <Card
            title={<PersonProfileTitle person={person}/>}
            className="profile large">

            <Tabs tabPosition="left" size="large">

                <Tabs.TabPane tab="Events" key="events">
                    <PersonProfileEvents
                        {...this.props}
                        {...this.state}
                        setSelected={this.setSelectedEvent}/>
                    <PersonProfileSources
                        {...this.props}
                        {...this.state}/>
                </Tabs.TabPane>

                <Tabs.TabPane tab="Gallery" disabled/>

            </Tabs>

        </Card>;

    }

    componentDidCatch(error: Error, errorInfo: React.ErrorInfo) {
        //TODO
        console.error(error);
    }

    componentDidUpdate(prevProps: Readonly<Props>) {
        if (this.props.person != prevProps.person || this.props.family != prevProps.family) {
            const relatives = this.props.person && determineRelatives(this.props.person.id, this.props.family);
            this.setState({relatives: relatives, events: this.determineEvents(this.props.person, relatives)});
        }
    }

    private determineEvents(person: Person.AsObject, relatives: Relatives): ReadonlyArray<ProfileEvent> {
        return this.determinePersonEvents(person)
            .concat(this.determineFamilyEvents(person, relatives));
    }

    private determinePersonEvents(person: Person.AsObject): ReadonlyArray<ProfileEvent> {
        if (!person) return [];
        return person.eventsList.map(event => ({event, person, type: "person", relationshipToMain: "self"}));
    }

    private determineFamilyEvents(person: Person.AsObject, relatives: Relatives): ReadonlyArray<ProfileEvent> {
        if (!person || !relatives) return [];
        const dates = lifeDateRange(person);
        const events: ProfileEvent[] = [];
        events.push(...relativeEvents(relatives.father, "parent", person, dates));
        events.push(...relativeEvents(relatives.mother, "parent", person, dates));
        for (const s of relatives.spouses) {
            events.push(...relativeEvents(s.spouse, "spouse", person, dates));
            s.children.forEach(child => events.push(...relativeEvents(child, "child", person, dates)));
        }
        for (const s of relatives.siblings) {
            events.push(...relativeEvents(s, "sibling", person, dates));
        }
        return events;
    }

}

function relativeEvents(relative: Person.AsObject, relationship: RelativeRelationship, mainPerson: Person.AsObject, dates: DateRange.AsObject): ReadonlyArray<ProfileEvent> {
    if (!relative) return [];
    const events: ProfileEvent[] = [];
    for (const event of relative.eventsList) {
        if (!retainFamilyEvent(event)) continue;
        if (!isDateOrdered(dates?.earliest, event.date, dates?.latest)) continue;
        events.push({event, person: relative, type: "family", relationshipToMain: relationship});
    }
    return events;
}

function retainFamilyEvent(event: Event.AsObject): boolean {
    switch (eventType(event)) {
        case "BIRTH":
        case "DEATH":
            return true;
        default:
            return false;
    }
}