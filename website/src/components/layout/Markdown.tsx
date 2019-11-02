import * as React from "react";
import {HasClass} from "../style/HasClass";

export const Markdown = (props: {text: string} & HasClass) => {
    const text = props.text;
    if (!text) return null;
    const paragraphs = text.split("\n\n");
    return <div {...props}>
        {paragraphs.length > 1 ? paragraphs.map(p => <p>{format(p)}</p>) : format(text)}
    </div>;
};

function format(s: string): React.ReactNode {
    return s;
}