import * as React from "react";
import {Name} from "../../protobuf/generated/name_pb";
import {UnknownName} from "./Name";

export const PersonName = (props: {name: Name.AsObject, showGender?: string}) => {
    let name = props.name;
    if (!name) name = UnknownName;
    return <span className={"name" + (name.unknown ? " unknown" : "")}>
        {name.value && !name.unknown ? name.value : ("Unnamed " + (props.showGender || ""))}
    </span>;
};