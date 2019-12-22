import * as React from "react";
import {HasClass} from "../style/HasClass";
import {Point} from "../../protobuf/generated/location_pb";
import {GeoJSON, Map, Marker, Popup, TileLayer} from 'react-leaflet';
import {Icon, LatLngLiteral} from "leaflet";
import {Loading} from "../style/Loading";
import {PlaceBundle} from "../../protobuf/generated/place_pb";
import "./PlaceMap.css";
import {toGeoJson} from "./position/GeoJson";

require('leaflet/dist/leaflet.css');

type Props = HasClass & {
    loading: boolean;
    places: ReadonlyArray<PlaceBundle.AsObject>;
};

type State = {
    places: ReadonlyArray<PlaceBundle.AsObject>;
    zoom: number;
    center: Point.AsObject;
    markers: ReadonlyArray<MarkerPoint>
    area: ReadonlyArray<Point.AsObject>
}

export class PlaceMap extends React.PureComponent<Props, State> {

    constructor(props: Props) {
        super(props);
        this.state = this.determineState(props);
    }

    render() {

        return <div className={(this.props.className || "") + " map"} style={this.props.style}>

            {this.props.loading && <Loading/>}

            {this.state.places.length && <MapComponent {...this.state}/>}

            {!this.state.places.length && <span className="unimportant" style={{padding: 4}}>
                No position information available.
            </span>}

        </div>;

    }

    componentDidUpdate(prevProps: Readonly<Props>) {
        if (this.props.places != prevProps.places)
            this.setState(this.determineState(this.props));
    }

    private determineState(props: Props): State {
        const places = props.places.filter(p => p && p.position);
        return {
            places: places,
            zoom: this.determineZoom(places),
            center: this.determineCenter(places),
            markers: places.filter(isPoint).map(toMarker),
            area: places.filter(isArea).map(p => p.position.boundaryList)[0]
        };
    }

    private determineZoom(places: PlaceBundle.AsObject[]) {
        return Math.min(...places.map(p => p.position.zoom || 0).filter(z => z > 0));
    }

    private determineCenter(positions: PlaceBundle.AsObject[]) {
        //FIXME average
        return positions[0]?.position.center;
    }

}

function isPoint(place: PlaceBundle.AsObject): boolean {
    return place.position && place.position.boundaryList.length <= 1;
}

function isArea(place: PlaceBundle.AsObject): boolean {
    return place.position && place.position.boundaryList.length >= 3;
}

function toMarker(place: PlaceBundle.AsObject): MarkerPoint {
    return {point: place.position.center, label: place.place.name};
}

const MapComponent = (props: {center: Point.AsObject, zoom: number, markers: ReadonlyArray<MarkerPoint>, area: ReadonlyArray<Point.AsObject>}) => {

    const center = props.center;
    if (!center) return null;

    const markers = props.markers || [];

    return <Map center={toLatLong(center)} zoom={props.zoom}>
        <TileLayer url={"https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"}/>
        {props.area && <GeoJSON data={toGeoJson(props.area)}/>}
        {markers.map(marker => <Marker position={toLatLong(marker.point)} icon={CustomIcon}>
            <Popup>{marker.label}</Popup>
        </Marker>)}
    </Map>;

};

function toLatLong(point: Point.AsObject): LatLngLiteral {
    return {lat: point.latitude, lng: point.longitude};
}

type MarkerPoint = {point: Point.AsObject, label: React.ReactNode}

const CustomIcon: Icon = new Icon({
    iconUrl: "/images/marker-icon.png",
    shadowUrl: "/images/marker-shadow.png",
    iconSize: [25, 41],
    iconAnchor: [12, 40]
});