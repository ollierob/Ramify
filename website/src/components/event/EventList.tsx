import * as React from "react";
import {Event} from "../../protobuf/generated/event_pb";
import {HasClass} from "../style/HasClass";
import {Card} from "antd";
import {FormattedDateRange} from "../date/FormattedDateRange";
import {eventType, isBirthEvent, isDeathEvent, isDeathOrBurialEvent, isPostDeathEvent} from "./Event";
import {isBirthOrBaptismRecord} from "../records/RecordType";

type Props = HasClass & {
    events: ReadonlyArray<Event.AsObject>
}

export class EventList extends React.PureComponent<Props> {

    render() {

        const events = this.props.events || [];

        return <Card
            className={"eventList " + (this.props.className || "")}
            title="Events"
            style={this.props.style}>

            {events.map(e => <EventBox event={e}/>)}

        </Card>;

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