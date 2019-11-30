import * as React from "react";
import {HasClass} from "../style/HasClass";

type State = {text: string, parsed: React.ReactNode}

export const Markdown = (props: {text: string} & HasClass) => {

    const [state, setState] = React.useState<State>();

    const text = props.text;
    if (!text) return null;

    let parsed = state ? state.parsed : null;
    if (!state || state.text != text) {
        parsed = parse(text);
        setState({text, parsed});
    }

    return <div {...props}>{parsed}</div>;

};

function parse(s: string): React.ReactNode {
    const split = s.split("\n\n");
    if (split.length > 1) return <>{split.map(s => <p>{formatParagraph(s)}</p>)}</>;
    return s;
}

function formatParagraph(s: string): React.ReactNode {
    return s;
}