import * as React from "react";
import * as ReactDOM from "react-dom";
import MapContainer, {MapMarker} from "./components/MapContainer";

export default class Router extends React.Component {

    render() {
        return <MapContainer
            zoom={13.5}
            center={{latitude: 53.74, longitude: -2.045}}
            markers={this.markers()}/>
    }

    private markers(): MapMarker[] {
        return [
            {id: "robertshaw", label: "Robertshaw", coords: {latitude: 53.754276, longitude: -2.0345895}, type: "farm"}
        ]
    }

}

ReactDOM.render(<Router/>, document.getElementById("main"));