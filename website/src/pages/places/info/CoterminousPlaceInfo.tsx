import * as  React from "react";
import {PlaceDescription} from "../../../protobuf/generated/place_pb";
import {Alert} from "antd";
import {PlaceLink} from "../../../components/places/PlaceLink";
import {joinComponents} from "../../../components/Components";

export const CoterminousPlaceInfo = (props: {description: PlaceDescription.AsObject}) => {

    const coterminous = props.description && props.description.coterminousList;
    if (!coterminous || !coterminous.length) return null;

    return <Alert
        type="info"
        showIcon
        message={<>This place is coterminous with {joinComponents(coterminous.map(c => <PlaceLink place={c} showType/>), ", ")}</>}/>;

};