import * as React from "react";
import {Card} from "antd";
import {Place, PlaceDescription} from "../../../protobuf/generated/place_pb";
import ChildPlaceCards from "../../../components/places/ChildPlaceCards";
import {PlaceTitle} from "../../../components/places/PlaceTitle";
import {PlaceFavouritesHandler} from "../../../components/places/PlaceFavourites";
import {NoRecordCards, RecordCards} from "../../../components/records/RecordCards";
import {RecordSet} from "../../../protobuf/generated/record_pb";
import {AsyncData} from "../../../components/fetch/AsyncData";
import {DefunctPlaceWarning} from "./DefunctPlaceWarning";
import ReactMarkdown = require("react-markdown");
import {isBuilding} from "../../../components/places/PlaceType";

type Props = PlaceFavouritesHandler & {
    place: Place.AsObject;
    description: PlaceDescription.AsObject;
    childPlaces?: ReadonlyArray<Place.AsObject>
    loadingChildren?: boolean;
    records?: AsyncData<ReadonlyArray<RecordSet.AsObject>>;
}

export const PlaceInfo = (props: Props) => {

    const place = props.place;
    if (!place) return null;

    const description = props.description;

    return <Card
        className="info"
        title={<PlaceTitle {...props}/>}>

        {place.defunct && <DefunctPlaceWarning
            description={description}
            type={isBuilding(place) ? "demolished" : "historic"}/>}

        {description && <Card className="description" title="Description" bordered={false}>
            <ReactMarkdown source={description.description}/>
        </Card>}

        {props.childPlaces && <Card className="places" title="Places" bordered={false}>
            <ChildPlaceCards
                {...props}
                childPlaces={props.childPlaces}
                alsoSeePlaces={description && description.alsoseeList}
                loading={props.loadingChildren}
                noPlacesMessage={<>No further places within this area have been registered.</>}/>
        </Card>}

        <Card className="records" title="Records" bordered={false}>
            {props.records ? <RecordCards
                loading={props.records.loading}
                records={props.records.data}/> : <NoRecordCards/>}
        </Card>

    </Card>;

};