import * as React from "react";
import {Card} from "antd";
import {Place, PlaceDescription} from "../../../protobuf/generated/place_pb";
import ChildPlaceCards from "../../../components/places/ChildPlaceCards";
import {placeTypeName} from "../../../components/places/PlaceType";
import {PlaceLinks} from "../../../components/places/PlaceLinks";
import ReactMarkdown = require("react-markdown");
import {PlaceTitle} from "../../../components/places/PlaceTitle";
import {PlaceFavouritesHandler} from "../../../components/places/PlaceFavourites";

type Props = PlaceFavouritesHandler & {
    place: Place.AsObject;
    description: PlaceDescription.AsObject;
    childPlaces: ReadonlyArray<Place.AsObject>
    loadingChildren: boolean;
}

export const AreaInfo = (props: Props) => {

    const place = props.place;
    if (!place) return null;

    const description = props.description;

    return <Card
        className="info"
        title={<PlaceTitle {...props}/>}>

        {description
            ? <ReactMarkdown
                className="description"
                source={description.description}/>
            : <div className="description">No description available.</div>}

        <ChildPlaceCards
            {...props}
            alsoSeePlaces={description && description.alsoseeList}
            loading={props.loadingChildren}/>

    </Card>

};