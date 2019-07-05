import * as React from "react";
import ReactMarkdown = require("react-markdown");

export const PlaceTypeDescription = (props: {description: string}) => {
    const description = props.description;
    if (!description) return null;
    return <div className="typeDescription">
        <ReactMarkdown source={description}/>
    </div>
}