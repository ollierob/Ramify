import * as React from "react";
import {EventBoxProps} from "./EventBox";

export const EventTitle = (props: EventBoxProps) => {
    return <>{props.event.title}</>;
};
