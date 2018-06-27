import * as React from "react";
import {ErrorInfo, WheelEvent} from "react";
import {MapCoordinates, Pin, SlippyMap} from "react-slippy-map";
import "./Markers.css"

interface MapContainerProps {
    zoom: number;
    center: MapCoordinates;
    markers?: MapMarker[];
    show: MapMarkerType[];
}

interface MapContainerState {
    zoom: number;
    center: MapCoordinates;
}

export default class MapContainer extends React.PureComponent<MapContainerProps, MapContainerState> {

    constructor(props: MapContainerProps) {
        super(props);
        this.state = {zoom: props.zoom, center: props.center};
    }

    render() {
        console.log("Zoom = " + this.state.zoom);
        const types = new Set(this.props.show);
        return <SlippyMap
            zoom={this.state.zoom}
            onWheel={e => this.onZoom(e)}
            center={this.state.center}
            onCenterChange={center => this.setState({center: center})}>
            {this.props.markers && this.props.markers.filter(marker => types.has(marker.type)).map(marker => this.toPin(marker))}
        </SlippyMap>
    }

    componentDidCatch(error: Error, info: ErrorInfo) {
        console.error(error);
    }

    private toPin(marker: MapMarker) {
        return <Pin key={marker.id} className={"pin " + marker.type} coords={marker.coords}>
            {this.state.zoom >= 15 && <div className="label">{marker.label}</div>}
        </Pin>
    }

    private onZoom(e: WheelEvent<any>): void {
        const zoomIn = e.deltaY < 0;
        if (zoomIn && this.state.zoom >= 18) return;
        else if (!zoomIn && this.state.zoom < 5) return;
        const x = e.screenX, y = e.screenY;
        this.setState(current => {
            return {
                zoom: current.zoom + (zoomIn ? +1 : -1),
                //center: {latitude: getLat(x, current.zoom), longitude: getLon(y, current.zoom)} //TODO figure out how to import {}
            }
        });
    }

    zoom(coords: MapCoordinates, zoom: number): void {
        this.setState({zoom: zoom, center: coords})
    }

}

export type MapMarkerType = "church" | "farm";

export interface MapMarker {
    id: string;
    label: string;
    type: MapMarkerType;
    coords: MapCoordinates;
}