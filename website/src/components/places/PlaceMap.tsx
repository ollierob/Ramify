import * as React from "react";
import {HasClass} from "../style/HasClass";
import {Point, Position} from "../../protobuf/generated/location_pb";
import {Map, Marker, Popup, TileLayer} from 'react-leaflet'
import {LatLngLiteral} from "leaflet";
import {Loading} from "../Loading";
import {Place, PlaceType} from "../../protobuf/generated/place_pb";

require('leaflet/dist/leaflet.css');

type Props = HasClass & {
    loading: boolean;
    place: Place.AsObject,
    position: Position.AsObject;
    area?: boolean
    defaultZoom?: number;
};

export class PlaceMap extends React.PureComponent<Props> {

    render() {

        return <div className={(this.props.className || "") + " map"} style={this.props.style}>

            {this.props.loading && <Loading/>}

            {this.props.position && <MapComponent
                {...this.props}
                zoom={this.props.position.zoom || this.props.defaultZoom || defaultZoom(this.props.place)}
                center={this.props.position.center}
                markers={!this.props.area && markerPoints(this.props.place, this.props.position)}
            />}

            {!this.props.position && <span className="unimportant" style={{padding: 4}}>
                No position information available.
            </span>}

        </div>;

    }

}

function markerPoints(place: Place.AsObject, position: Position.AsObject): ReadonlyArray<MarkerPoint> {
    if (position.boundaryList.length) return [];
    return [{point: position.center, label: place.name}]
}

type MapProps = {
    center: Point.AsObject;
    zoom: number;
    markers: ReadonlyArray<MarkerPoint>
}

const MapComponent = (props: MapProps) => {

    const center = props.center;
    if (!center) return null;

    const markers = props.markers || [];

    return <Map center={toLatLong(center)} zoom={props.zoom}>
        <TileLayer
            url={"https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"}/>
        {markers.map(marker => <Marker position={toLatLong(marker.point)}>
            <Popup>{marker.label}</Popup>
        </Marker>)}
    </Map>

}

function toLatLong(point: Point.AsObject): LatLngLiteral {
    return {lat: point.latitude, lng: point.longitude}
}

type MarkerPoint = {point: Point.AsObject, label: React.ReactNode}

function defaultZoom(place: Place.AsObject): number {
    switch (place.type) {
        case PlaceType.COUNTRY:
            return 4;
        case PlaceType.COUNTY:
            return 8;
        case PlaceType.PARISH:
        case PlaceType.CHAPELRY:
            return 11;
        case PlaceType.TOWNSHIP:
            return 12;
        case PlaceType.TOWN:
            return 13;
        case PlaceType.VILLAGE:
        case PlaceType.HAMLET:
            return 14;
        case PlaceType.FARMSTEAD:
            return 15;
        case PlaceType.SCHOOL:
            return 16;
        case PlaceType.INN:
            return 17;
        default:
            return 10;
    }
}