import * as React from "react";
import {Place, PlaceDescription} from "../../../protobuf/generated/place_pb";
import {HasClass} from "../../../components/style/HasClass";
import {Card} from "antd";
import {PlaceLink} from "../../../components/places/PlaceLink";
import {Loading} from "../../../components/style/Loading";

type Props = HasClass & {
    loading: boolean;
    place: Place.AsObject;
    description: PlaceDescription.AsObject;
    preTitle?: React.ReactNode;
}

export class RecordPlaceInfo extends React.PureComponent<Props> {

    render() {

        if (this.props.loading) return <Loading/>;
        if (!this.props.place) return null;

        return <Card
            className={"placeInfo " + (this.props.className || "")}
            style={this.props.style}
            title={<>{this.props.preTitle}<PlaceLink place={this.props.place} showType/></>}>

            {this.props.description && this.props.description.description || <i>No description available.</i>}

        </Card>;

    }

};