declare module "react-slippy-map" {

    interface SlippyMapProps {
        zoom?: number;
        center?: { latitude: number, longitude: number }
        baseTilesUrl?: string;
        onClick?: (event: any) => void;
    }

    class SlippyMap extends React.Component<SlippyMapProps, any> {

    }

}