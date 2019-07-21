import * as React from "react";
import {Alert, Card, Icon} from "antd";
import {Place, PlaceDescription} from "../../../protobuf/generated/place_pb";
import ChildPlaceCards from "../../../components/places/ChildPlaceCards";
import {PlaceTitle} from "../../../components/places/PlaceTitle";
import {PlaceFavouritesHandler} from "../../../components/places/PlaceFavourites";
import ReactMarkdown = require("react-markdown");
import {RecordCards} from "../../../components/records/RecordCards";
import {RecordSet} from "../../../protobuf/generated/record_pb";
import {AsyncData} from "../../../components/fetch/AsyncData";
import {NoData} from "../../../components/style/NoData";
import {PlaceContextMenu} from "../../../components/places/PlaceContextMenu";
import {joinComponents} from "../../../components/Components";

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

        {place.defunct && <DefunctWarning description={description}/>}

        {description
            ? <ReactMarkdown
                className="description"
                source={description.description}/>
            : <div className="description">No description available.</div>}

        {props.records && <Card className="records" title="Records" bordered={false}>
            <RecordCards
                loading={props.records.loading}
                records={props.records.data}
                noRecordsMessage={<>No records specific to this place have been registered.</>}/>
        </Card>}

        {props.childPlaces && <Card className="places" title="Places" bordered={false}>
            <ChildPlaceCards
                {...props}
                childPlaces={props.childPlaces}
                alsoSeePlaces={description && description.alsoseeList}
                loading={props.loadingChildren}
                noPlacesMessage={<>No further places within this area have been registered.</>}/>
        </Card>}

    </Card>;

};

const DefunctWarning = (props: {description: PlaceDescription.AsObject}) => {
    const laterBecame = props.description ? props.description.laterbecameList : [];
    return <Alert
        type="warning"
        className="defunct"
        showIcon
        message={<>This place is historic.</>}
        description={laterBecame.length > 0 && <>
            It later became part of {joinComponents(laterBecame.map(p => <PlaceContextMenu place={p} showType/>), ", ")}
        </>}/>;
};