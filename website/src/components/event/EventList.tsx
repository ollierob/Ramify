import * as React from "react";
import {Event} from "../../protobuf/generated/event_pb";
import {HasClass} from "../style/HasClass";
import {Card} from "antd";
import {FormattedDateRange} from "../date/FormattedDateRange";

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

    return <Card className="event">
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