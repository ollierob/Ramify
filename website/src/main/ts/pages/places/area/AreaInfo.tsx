import * as React from "react";
import {Card} from "antd";
import {Place} from "../../../protobuf/generated/place_pb";
import ChildPlaceCards from "../../../components/places/ChildPlaceCards";

type Props = {
    place: Place.AsObject;
    childPlaces: ReadonlyArray<Place.AsObject>
    loadingChildren: boolean;
}

export const AreaInfo = (props: Props) => {

    const place = props.place;
    if (!place) return null;

    return <Card
        className="info"
        title={<><b>{place.name}</b></>}>

        <ChildPlaceCards
            {...props}
            loading={props.loadingChildren}/>

    </Card>

}