import * as  React from "react";
import {Relatives} from "../../../components/relationship/Relatives";
import {ProfileEvent, sortProfileEvents} from "../../../components/event/ProfileEvent";
import {AsyncData, asyncLoadData} from "../../../components/fetch/AsyncData";
import {Event} from "../../../protobuf/generated/event_pb";
import {EventList} from "../../../components/event/EventList";
import CheckableTag from "antd/es/tag/CheckableTag";
import {Person} from "../../../protobuf/generated/person_pb";
import {Family} from "../../../protobuf/generated/family_pb";
import {FamilyTreeId} from "../../../components/tree/FamilyTree";
import {LoadingIcon} from "../../../components/images/Icons";
import {lifeDateRange} from "../../../components/people/Person";
import * as Cookies from 'es-cookie';
import {DEFAULT_EVENT_LOADER} from "../../../components/event/EventLoader";

type Props = {
    person: Person.AsObject;
    family: Family.AsObject;
    tree: FamilyTreeId;
    relatives: Relatives
    events: ReadonlyArray<ProfileEvent>
}

type State = {
    showOwn: boolean;
    showFamily?: boolean;
    showHistoric?: boolean;
    historic: AsyncData<ReadonlyArray<Event.AsObject>>
}

const FAMILY_COOKIE = "showProfileFamilyEvents";
const HISTORIC_COOKIE = "showProfileHistoricEvents";

export class PersonProfileEvents extends React.PureComponent<Props, State> {

    private readonly eventLoader = DEFAULT_EVENT_LOADER;

    constructor(props: Props) {
        super(props);
        this.state = {
            showOwn: true,
            showFamily: Cookies.get(FAMILY_COOKIE) == "true",
            showHistoric: Cookies.get(HISTORIC_COOKIE) == "true",
            historic: {}
        };
    }

    render() {

        return <div className="events">

            <div className="controls">

                <CheckableTag checked={this.state.showOwn} onChange={c => this.setState({showOwn: c})}>
                    Own events
                </CheckableTag>

                <CheckableTag checked={this.state.showFamily} onChange={c => this.setState({showFamily: c})}>
                    Family events
                </CheckableTag>

                <CheckableTag checked={this.state.showHistoric} onChange={c => this.setState({showHistoric: c})}>
                    Historic events {this.state.historic.loading && <LoadingIcon/>}
                </CheckableTag>

            </div>

            <EventList
                {...this.props}
                {...this.state}
                events={this.events()}/>

        </div>;

    }

    private events(): ReadonlyArray<ProfileEvent> {
        return this.props.events.concat(this.historicEvents())
            .filter(e => showEvent(e, this.state))
            .sort(sortProfileEvents);
    }

    private historicEvents(): ReadonlyArray<ProfileEvent> {
        if (!this.state.historic.data) return [];
        return this.state.historic.data.map<ProfileEvent>(event => ({event, type: "historic", relationshipToMain: "self"}));
    }

    componentDidMount() {
        if (this.state.showHistoric)
            this.loadHistoric(this.props.person);
    }

    componentDidUpdate(prevProps: Readonly<Props>, prevState: Readonly<State>) {
        if (this.props.events != prevProps.events)
            this.setState({historic: {}});
        if (this.state.showHistoric && ((!prevState.showHistoric && !this.state.historic.data) || this.props.person != prevProps.person))
            this.loadHistoric(this.props.person);
        if (this.state.showFamily != prevState.showFamily)
            Cookies.set(FAMILY_COOKIE, String(this.state.showFamily));
        if (this.state.showHistoric != prevState.showHistoric)
            Cookies.set(HISTORIC_COOKIE, String(this.state.showHistoric));
    }

    private loadHistoric(person: Person.AsObject) {
        if (!person || !person.eventsList) return;
        const date = lifeDateRange(person);
        if (!date) return;
        const places = new Set(person.eventsList.map(event => event.place?.id).filter(id => !!id));
        asyncLoadData(
            date,
            d => this.eventLoader.loadHistoricEvents(d.earliest, d.latest, places),
            historic => this.setState({historic}));
    }

}

function showEvent(event: ProfileEvent, options: State): boolean {
    if (event == null) return false;
    switch (event.type) {
        case "person":
            return options.showOwn;
        case "family":
            return options.showFamily;
        case "historic":
            return options.showHistoric;
    }
}