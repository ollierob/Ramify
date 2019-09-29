import * as React from "react";
import {PlaceDescription} from "../../../protobuf/generated/place_pb";
import {joinComponents} from "../../../components/Components";
import {PlaceLink} from "../../../components/places/PlaceLink";
import {Alert} from "antd";

export const DefunctPlaceWarning = (props: {description: PlaceDescription.AsObject, type: DefunctType}) => {

    const laterBecame = props.description ? props.description.laterbecameList : [];

    const description = laterBecame.length > 0
        ? <>It later became part of {joinComponents(laterBecame.map(p => <PlaceLink place={p} showType/>), ", ")}</>
        : <>It no longer exists</>;

    return <Alert
        type="warning"
        className="defunct"
        showIcon
        message={defunctDescription(props.type)}
        description={description}/>;

};

type DefunctType = "historic" | "demolished"

function defunctDescription(type: DefunctType): string {
    switch (type) {
        case "historic":
            return "This place is historic";
        case "demolished":
            return "This building has been demolished";
    }
}