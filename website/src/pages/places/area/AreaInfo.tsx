import * as React from "react";
import {Card} from "antd";
import {Place, PlaceDescription} from "../../../protobuf/generated/place_pb";
import ChildPlaceCards from "../../../components/places/ChildPlaceCards";
import {PlaceTitle} from "../../../components/places/PlaceTitle";
import {PlaceFavouritesHandler} from "../../../components/places/PlaceFavourites";
import ReactMarkdown = require("react-markdown");
import {RecordCards} from "../../../components/records/RecordCards";
import {RecordSet} from "../../../protobuf/generated/record_pb";
import {AsyncData} from "../../../components/fetch/AsyncData";
import {NoData} from "../../../components/style/NoData";

type Props = PlaceFavouritesHandler & {
    place: Place.AsObject;
    description: PlaceDescription.AsObject;
    childPlaces: ReadonlyArray<Place.AsObject>
    loadingChildren: boolean;
    records: AsyncData<ReadonlyArray<RecordSet.AsObject>>;
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

        <Card className="records" title="Records" bordered={false}>
            <RecordCards
                loading={props.records.loading}
                records={props.records.data}
                noRecordsMessage={<>No records specific to this place have been registered.</>}/>
        </Card>

        <Card className="places" title="Places" bordered={false}>
            <ChildPlaceCards
                {...props}
                alsoSeePlaces={description && description.alsoseeList}
                loading={props.loadingChildren}
                noPlacesMessage={<>No further places within this area have been registered.</>}/>
        </Card>

    </Card>;

};