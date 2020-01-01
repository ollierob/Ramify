import * as React from "react";

export function joinComponents(nodes: ReadonlyArray<React.ReactNode>, join: React.ReactNode): React.ReactNode {
    if (!nodes || !nodes.length) return null;
    nodes = nodes.filter(n => !!n);
    switch (nodes.length) {
        case 0:
            return null;
        case 1:
            return nodes[0];
        default:
            return nodes.reduce((p, c) => [p, join, c]);
    }
}