import * as React from "react";
import {Card} from "antd";
import {Place} from "../../../protobuf/generated/place_pb";
import ChildPlaceCards from "../../../components/places/ChildPlaceCards";
import {placeTypeName} from "../../../components/places/PlaceType";

type Props = {
    place: Place.AsObject;
    typeDescription: string;
    childPlaces: ReadonlyArray<Place.AsObject>
    loadingChildren: boolean;
}

export const AreaInfo = (props: Props) => {

    const place = props.place;
    if (!place) return null;

    return <Card
        className="info"
        title={<AreaTitle {...props}/>}>

        <ChildPlaceCards
            {...props}
            loading={props.loadingChildren}/>

    </Card>

}

const AreaTitle = (props: {place: Place.AsObject}) => {
    const place = props.place;
    return <>
        <b>{place.name}</b>
        {" "}
        <span className="unimportant">{placeTypeName(place.type)}</span>
    </>
}