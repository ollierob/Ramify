import * as React from "react";
import {Tag} from "antd";
import {AddTagIcon} from "../images/Icons";

export const PersonLabels = (props: {labels: ReadonlyArray<string>}) => {

    if (!props.labels) return null;

    return <div className="labels">
        {props.labels.map(label => <Tag className="label" closable key={label}>{label}</Tag>)}
        <Tag className="label add" key="!add"><AddTagIcon/> Add tag</Tag>
    </div>;

};