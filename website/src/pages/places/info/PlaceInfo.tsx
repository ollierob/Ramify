import * as React from "react";
import {Card} from "antd";
import {Place, PlaceDescription} from "../../../protobuf/generated/place_pb";
import ChildPlaceCards from "../../../components/places/ChildPlaceCards";
import {PlaceTitle} from "../../../components/places/PlaceTitle";
import {PlaceFavouritesHandler} from "../../../components/places/PlaceFavourites";
import {RecordSetChildCards} from "../../../components/records/RecordSetChildCards";
import {RecordSet} from "../../../protobuf/generated/record_pb";
import {AsyncData} from "../../../components/fetch/AsyncData";
import {Markdown} from "../../../components/layout/Markdown";

type Props = PlaceFavouritesHandler & {
    place: Place.AsObject;
    description: PlaceDescription.AsObject;
    childPlaces?: ReadonlyArray<Place.AsObject>
    loadingChildren?: boolean;
    records?: AsyncData<ReadonlyArray<RecordSet.AsObject>>;
    card?: boolean;
    showAlsoSee?: boolean;
}

export class PlaceInfo extends React.PureComponent<Props> {

    render() {

        const place = this.props.place;
        if (!place) return null;

        const description = this.props.description;

        const inner = <>

            <div className="description">
                {description ? <Markdown text={description.description}/> : NoDescription}
            </div>

            {this.props.childPlaces && (this.props.childPlaces.length || !place.type.isbuilding) && <Card className="places" title={<b>Places</b>} bordered={false}>
                <ChildPlaceCards
                    {...this.props}
                    childPlaces={this.props.childPlaces}
                    alsoSeePlaces={this.props.showAlsoSee && description && description.alsoseeList}
                    loading={this.props.loadingChildren}
                    noPlacesMessage={<>No further places within this area have been registered.</>}/>
            </Card>}

            <Card className="records" title={<b>Records</b>} bordered={false}>
                <RecordSetChildCards
                    removePrefix={place.name + " "}
                    loading={this.props.records && this.props.records.loading}
                    records={this.props.records && this.props.records.data}/>
            </Card></>;

        if (this.props.card) return <Card
            className="info"
            title={<PlaceTitle {...this.props}/>}>
            {inner}
        </Card>;

        return inner;

    }

};

const NoDescription = <i>No description of this place is available.</i>;