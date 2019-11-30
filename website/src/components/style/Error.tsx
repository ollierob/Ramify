import * as React from "react";
import {HasClass} from "./HasClass";

export const ErrorMessage = (props: {message: string} & HasClass) => {
    if (!props.message) return null;
    return <div style={props.style} className={"error " + props.className}>
        {props.message}
    </div>;
};