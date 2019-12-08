import * as React from "react";
import {HasClass} from "../style/HasClass";
import {Point, Position} from "../../protobuf/generated/location_pb";
import {Map, Marker, Popup, TileLayer} from 'react-leaflet';
import {LatLngLiteral} from "leaflet";
import {Loading} from "../style/Loading";
import {Place} from "../../protobuf/generated/place_pb";
import "./PlaceMap.css";

require('leaflet/dist/leaflet.css');

type Props = HasClass & {
    loading: boolean;
    place: Place.AsObject,
    position: Position.AsObject;
    area?: boolean
    defaultZoom?: number;
};

export const PlaceMap = (props: Props) => {

    return <div className={(props.className || "") + " map"} style={props.style}>

        {props.loading && <Loading/>}

        {props.position && <MapComponent
            {...props}
            zoom={props.position.zoom || props.defaultZoom}
            center={props.position.center}
            markers={!props.area && markerPoints(props.place, props.position)}
        />}

        {!props.position && <span className="unimportant" style={{padding: 4}}>
                No position information available.
            </span>}

    </div>;

};

function markerPoints(place: Place.AsObject, position: Position.AsObject): ReadonlyArray<MarkerPoint> {
    if (position.boundaryList.length) return [];
    return [{point: position.center, label: place.name}];
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
    </Map>;

};

function toLatLong(point: Point.AsObject): LatLngLiteral {
    return {lat: point.latitude, lng: point.longitude};
}

type MarkerPoint = {point: Point.AsObject, label: React.ReactNode}