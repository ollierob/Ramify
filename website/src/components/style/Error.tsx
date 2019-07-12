import * as React from "react";

export const ErrorMessage = (props: {message: string}) => {
    if (!props.message) return null;
    return <div className="error">{props.message}</div>;
}