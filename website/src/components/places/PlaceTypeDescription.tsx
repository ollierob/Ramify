import * as React from "react";
import {Markdown} from "../layout/Markdown";

export const PlaceTypeDescription = (props: {description: string}) => {
    const description = props.description;
    if (!description) return null;
    return <div className="typeDescription">
        <Markdown text={description}/>
    </div>;
};