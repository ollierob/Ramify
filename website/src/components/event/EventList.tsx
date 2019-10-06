import * as React from "react";
import {Event} from "../../protobuf/generated/event_pb";
import {Card} from "antd";
import {FormattedDateRange} from "../date/FormattedDateRange";
import {isBirthEvent, isDeathEvent, isPostDeathEvent} from "./Event";

type Props = {
    events: ReadonlyArray<Event.AsObject>
}

export class EventList extends React.PureComponent<Props> {

    render() {

        const events = this.props.events || [];

        return <>
            {events.map(e => <EventBox event={e}/>)}
        </>;

    }

}

const EventBox = (props: {event: Event.AsObject}) => {

    const event = props.event;

    return <Card className={"event " + eventClass(event)}>
        <div className="top">
            <div className="title">
                {event.title}
            </div>
            <div className="date">
                <FormattedDateRange accuracy="day" date={event.date}/>
            </div>
        </div>
    </Card>;

};

function eventClass(event: Event.AsObject): string {
    if (isBirthEvent(event)) return "birth";
    if (isDeathEvent(event)) return "death";
    if (isPostDeathEvent(event)) return "postDeath";
    return "postBirth";
}