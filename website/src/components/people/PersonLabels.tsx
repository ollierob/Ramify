import * as React from "react";
import {Tag} from "antd";

export const PersonLabels = (props: {labels: ReadonlyArray<string>}) => {

    if (!props.labels) return null;

    return <div className="labels">
        {props.labels.map(label => <Tag className="label">{label}</Tag>)}
    </div>;

};