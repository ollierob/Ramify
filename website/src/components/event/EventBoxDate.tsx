import * as React from "react";
import {EventBoxProps} from "./EventBox";
import {isExactRange, isInMonth} from "../date/DateRange";
import {EmptyPrefixWords, formatDateRange, formatYearRange} from "../date/DateFormat";
import {MonthDayWordsFormatter} from "../date/DateFormatter";
import {eventType, isBirthEvent, isPostDeathEvent} from "./Event";
import {Button, Checkbox, Icon, Modal} from "antd";
import {Person} from "../../protobuf/generated/person_pb";
import {Event} from "../../protobuf/generated/event_pb";
import {renderAge} from "../people/Age";
import {Loading} from "../style/Loading";
import {DateRange} from "../../protobuf/generated/date_pb";
import {DEFAULT_FAMILY_TREE_LOADER} from "../tree/FamilyTreeLoader";
import {AsyncData, asyncLoadData} from "../fetch/AsyncData";
import {PersonName} from "../people/PersonName";

type Props = EventBoxProps;

type State = {
    modalOpen?: boolean;
    checked: Set<string>;
    inferredBirthDate: AsyncData<DateRange.AsObject>
}

export class EventBoxDate extends React.PureComponent<Props, State> {

    private readonly treeLoader = DEFAULT_FAMILY_TREE_LOADER; //FIXME pass down
    private readonly closeModal = () => this.setState({modalOpen: false});

    constructor(props: Props) {
        super(props);
        this.state = {
            checked: allEventIds(props.person),
            inferredBirthDate: {}
        };
        this.inferBirth = this.inferBirth.bind(this);
    }

    render() {

        const event = this.props.event;
        if (!event) return null;
        const date = event.date;
        const exactDate = isExactRange(date);
        const exactMonth = !exactDate && isInMonth(date);

        const person = this.props.person;
        if (!person) return null;

        const anyEvents = this.props.person.eventsList != null && this.props.person.eventsList.length > 0;

        return <>

            <div className="year">{formatYearRange(date, EmptyPrefixWords)}</div>

            {(exactDate || exactMonth) && <div className="date">{formatDateRange(date, MonthDayWordsFormatter, EmptyPrefixWords)}</div>}

            {!exactDate && eventType(event) == "BIRTH" && <div className="date compute">
                <a onClick={() => this.setState({modalOpen: true})}>Compute</a>
            </div>}

            <Modal
                className="inferBirth"
                title={<>Compute birth date of <PersonName name={person.name}/></>}
                visible={this.state.modalOpen}
                onCancel={this.closeModal}
                footer={<>
                    <Button onClick={this.inferBirth} type="primary" disabled={!anyEvents || this.state.inferredBirthDate.loading}>Compute</Button>
                    <Button onClick={this.closeModal}><Icon type="close"/> Close</Button></>}>

                {anyEvents ? <>Use the following events:</> : <>No events to compute from</>}

                {this.props.person.eventsList.map(e => <EventCheckbox
                    key={e.id}
                    event={e}
                    checked={this.state.checked.has(e.id)}
                    disabled={this.state.inferredBirthDate.loading}
                    onCheck={checked => this.toggle(e.id, checked)}/>)}

                {anyEvents && !this.state.inferredBirthDate.loaded && <>Click below to compute.</>}
                {this.state.inferredBirthDate.loading && <Loading/>}
                {this.state.inferredBirthDate.loaded && <>Date: {this.state.inferredBirthDate.data ? formatDateRange(this.state.inferredBirthDate.data, "day") : "Could not compute from given events"}</>}

            </Modal>

        </>;

    }

    componentDidUpdate(prevProps: Readonly<Props>) {
        if (this.props.person && this.props.person != prevProps.person)
            this.setState({modalOpen: false, checked: allEventIds(this.props.person), inferredBirthDate: {}});
    }

    private toggle(id: string, checked: boolean) {
        this.setState(current => {
            const copy = new Set<string>(current.checked);
            if (checked) copy.add(id);
            else copy.delete(id);
            return {checked: copy};
        });
    }

    private inferBirth() {
        const checked = Array.from(this.state.checked);
        const all = checked.length == allEventIds(this.props.person).size;
        asyncLoadData(
            null,
            () => this.treeLoader.loadInferredBirthDate(this.props.tree, this.props.person.id, all ? [] : checked),
            inferredBirthDate => this.setState({inferredBirthDate}));
    }

}

function allEventIds(person: Person.AsObject): Set<string> {
    if (!person || !person.eventsList) return new Set();
    return new Set(person.eventsList.map(e => e.id));
}

const EventCheckbox = (props: {event: Event.AsObject, checked: boolean, disabled?: boolean, onCheck: (checked: boolean) => void}) => {
    const event = props.event;
    if (!event || isBirthEvent(event) || isPostDeathEvent(event)) return null;
    return <Checkbox className="date" id={event.id} {...props} onChange={e => props.onCheck(e.target.checked)}>
        <b>{event.title}</b>
        &nbsp;- {formatDateRange(event.date, "day", {between: "between "})}
        {event.givenage && <>&nbsp;- age {renderAge(event.givenage)}</>}
    </Checkbox>;
};