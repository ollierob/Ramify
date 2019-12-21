import * as React from "react";
import {HasClass} from "../components/style/HasClass";

export const SubMenu = (props: {children: React.ReactNode} & HasClass) => {
    return <div className={"submenu " + (props.className || "")} style={props.style}>
        {props.children}
    </div>;
};