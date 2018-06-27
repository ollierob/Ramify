declare module "react-slippy-map" {

    import {WheelEventHandler} from "react";

    type Coordinates = { latitude: number, longitude: number };

    interface SlippyMapProps {
        zoom?: number;
        center?: Coordinates;
        baseTilesUrl?: string;
        onClick?: (event: any) => void;
        onWheel?: WheelEventHandler<any>;
        onCenterChange?: (center: Coordinates) => void;
    }

    class SlippyMap extends React.Component<SlippyMapProps, any> {

    }

    interface PinProps {
        coords: Coordinates;
        offset: number;
        zoom: number;
    }

    class Pin extends React.Component<PinProps, any> {

    }

    function getX(lon: number, zoom: number): number;

    function getY(lat: number, zoom: number): number;

    function getLon(x: number, zoom: number): number;

    function getLat(y: number, zoom: number): number;

}