import * as React from "react";
import {Card} from "antd";
import {Place, PlaceDescription} from "../../../protobuf/generated/place_pb";
import ChildPlaceCards from "../../../components/places/ChildPlaceCards";
import {PlaceTitle} from "../../../components/places/PlaceTitle";
import {PlaceFavouritesHandler} from "../../../components/places/PlaceFavourites";
import {NoRecordCards, RecordCards} from "../../../components/records/RecordCards";
import {RecordSet} from "../../../protobuf/generated/record_pb";
import {AsyncData} from "../../../components/fetch/AsyncData";
import {isBuilding} from "../../../components/places/PlaceType";
import ReactMarkdown = require("react-markdown");

type Props = PlaceFavouritesHandler & {
    place: Place.AsObject;
    description: PlaceDescription.AsObject;
    childPlaces?: ReadonlyArray<Place.AsObject>
    loadingChildren?: boolean;
    records?: AsyncData<ReadonlyArray<RecordSet.AsObject>>;
    card?: boolean;
}

export const PlaceInfo = (props: Props) => {

    const place = props.place;
    if (!place) return null;

    const description = props.description;

    const inner = <>{description && <ReactMarkdown source={description.description}/>}

        {props.childPlaces && (props.childPlaces.length || !isBuilding(place)) && <Card className="places" title="Places" bordered={false}>
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
        </Card></>;

    if (props.card) return <Card
        className="info"
        title={<PlaceTitle {...props}/>}>
        {inner}
    </Card>;

    return inner;

};