import * as React from "react";
import {Event} from "../../protobuf/generated/event_pb";

export const EventNotes = (props: {event: Event.AsObject}) => {

    const event = props.event;
    if (!event) return null;

    return <div className="notes">
        {event.notes && <>Notes: {event.notes}</>}
        {event.cause && <>Cause: {event.cause}</>}
        {event.occupation && <>Occupation: {event.occupation}</>}
    </div>;

};