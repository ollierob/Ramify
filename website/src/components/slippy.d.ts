declare module "react-slippy-map" {

    import {WheelEventHandler} from "react";

    type MapCoordinates = { latitude: number, longitude: number };
    type ScreenCoordinates = { x: number, y: number };

    interface SlippyMapProps {
        zoom?: number;
        center?: MapCoordinates;
        baseTilesUrl?: string;
        onClick?: (event: any) => void;
        onWheel?: WheelEventHandler<any>;
        onCenterChange?: (center: MapCoordinates) => void;
    }

    class SlippyMap extends React.Component<SlippyMapProps, any> {

    }

    interface PinProps {
        coords: MapCoordinates;
        className?: string;
    }

    class Pin extends React.Component<PinProps, any> {

    }

    function getX(lon: number, zoom: number): number;

    function getY(lat: number, zoom: number): number;

    function getLon(x: number, zoom: number): number;

    function getLat(y: number, zoom: number): number;

}