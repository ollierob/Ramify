import * as React from "react";

export const Flag = (props: {iso: string}) => {
    const iso = props.iso;
    if (!iso) return null;
    return <img src={"/images/flags/" + iso.toUpperCase() + ".svg"} className="flag"/>
};