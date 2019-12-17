import * as React from "react";
import {Event} from "../../protobuf/generated/event_pb";
import {renderAge} from "../people/Age";

export const EventNotes = (props: {event: Event.AsObject}) => {

    const event = props.event;
    if (!event) return null;

    return <ul className="notes">
        {event.givenage && <li>Age: {renderAge(event.givenage)}</li>}
        {event.notes && <li>Notes: {event.notes}</li>}
        {event.cause && <li>Cause: {event.cause}</li>}
        {event.occupation && <li>Occupation: {event.occupation}</li>}
    </ul>;

};