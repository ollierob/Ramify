import * as React from "react";
import {Event} from "../../protobuf/generated/event_pb";
import {EventBox} from "./EventBox";

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