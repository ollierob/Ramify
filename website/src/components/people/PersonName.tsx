import * as React from "react";
import {Name} from "../../protobuf/generated/name_pb";

export const PersonName = (props: {name: Name.AsObject}) => {
    const name = props.name;
    if (!name || !name.value) return null;
    return <span className="name">{name.value}</span>;
};