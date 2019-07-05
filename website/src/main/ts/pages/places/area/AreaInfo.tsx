import * as React from "react";
import {Card} from "antd";
import {Place, PlaceDescription} from "../../../protobuf/generated/place_pb";
import ChildPlaceCards from "../../../components/places/ChildPlaceCards";
import {placeTypeName} from "../../../components/places/PlaceType";
import {PlaceLinks} from "../../../components/places/PlaceLinks";
import ReactMarkdown = require("react-markdown");

type Props = {
    place: Place.AsObject;
    description: PlaceDescription.AsObject;
    childPlaces: ReadonlyArray<Place.AsObject>
    loadingChildren: boolean;
}

export const AreaInfo = (props: Props) => {

    const place = props.place;
    if (!place) return null;

    return <Card
        className="info"
        title={<AreaTitle {...props}/>}>

        {props.description && <ReactMarkdown
            className="description"
            source={props.description.description}/>}

        <ChildPlaceCards
            {...props}
            alsoSeePlaces={props.description && props.description.alsoseeList}
            loading={props.loadingChildren}/>

    </Card>

};

const AreaTitle = (props: {place: Place.AsObject, description: PlaceDescription.AsObject;}) => {
    const place = props.place;
    return <>
        <b>{place.name}</b>
        {" "}
        <span className="unimportant">{placeTypeName(place.type)}</span>
        <PlaceLinks links={props.description && props.description.linkList}/>
    </>
};