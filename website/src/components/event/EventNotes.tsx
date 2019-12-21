import * as React from "react";
import {Event} from "../../protobuf/generated/event_pb";
import {renderAge} from "../people/Age";

export const EventNotes = (props: {event: Event.AsObject}) => {

    const event = props.event;
    if (!event) return null;

    return <ul className="notes">
        {event.givenage && <li>Given age: {renderAge(event.givenage)}</li>}
        {event.notes && <li className="notes">Notes: {event.notes}</li>}
        {event.cause && <li className="cause">Cause: {event.cause}</li>}
        {event.occupation && <li className="occupation">Occupation: {event.occupation}</li>}
        {event.description && <li className="description">{event.description}</li>}
    </ul>;

};