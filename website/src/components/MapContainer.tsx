import * as React from "react";
import {ErrorInfo, WheelEvent} from "react";
import {MapCoordinates, Pin, ScreenCoordinates, SlippyMap} from "react-slippy-map";
import "./Markers.css"

interface MapContainerProps {
    zoom: number;
    center: MapCoordinates;
    markers?: MapMarker[];
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
        return <SlippyMap
            zoom={this.state.zoom}
            onWheel={e => this.onZoom(e)}
            center={this.state.center}
            onCenterChange={center => this.setState({center: center})}>
            {this.props.markers && this.props.markers.map(marker => <Pin className={"pin " + marker.type} zoom={this.state.zoom} coords={marker.coords} offset={marker.offset}>{marker.label}</Pin>)}
        </SlippyMap>
    }

    componentDidCatch(error: Error, info: ErrorInfo) {
        console.error(error);
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

}

export interface MapMarker {
    label: string;
    type: "church" | "farm";
    coords: MapCoordinates;
    offset: ScreenCoordinates;
}