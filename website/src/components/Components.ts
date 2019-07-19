import * as React from "react";

export function joinComponents(nodes: ReadonlyArray<React.ReactNode>, join: React.ReactNode): React.ReactNode {
    if (!nodes.length) return null;
    if (nodes.length == 1) return nodes[0];
    return nodes.reduce((p, c) => [p, join, c]);
}