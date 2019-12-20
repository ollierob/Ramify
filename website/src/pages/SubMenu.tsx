import * as React from "react";

export const SubMenu = (props: {children: React.ReactNode}) => {
    return <div className="submenu">
        {props.children}
    </div>;
};