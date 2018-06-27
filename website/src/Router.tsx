import * as React from "react";
import * as ReactDOM from "react-dom";
import MapContainer, {MapMarker} from "./components/MapContainer";

export default class Router extends React.Component {

    render() {
        return <MapContainer
            zoom={13.5}
            center={{latitude: 53.74, longitude: -2.045}}
            show={["church", "farm"]}
            markers={this.markers()}/>
    }

    private markers(): MapMarker[] {
        return [
            {id: "robertshaw", label: "Robertshaw", coords: {latitude: 53.754276, longitude: -2.0345895}, type: "farm"},
            {id: "strinesclough", label: "Strines Clough", coords: {latitude: 53.7486632, longitude: -2.0780307}, type: "farm"},
            {id: "stthomasold", label: "St Thomas a Beckett", coords: {latitude: 53.7488916, longitude: -2.0215702}, type: "church"},
            {id: "stthomasnew", label: "St Thomas the Apostle", coords: {latitude: 53.7483968, longitude: -2.021994}, type: "church"},
            {id: "shorebaptist", label: "Shore Baptist", coords: {latitude: 53.7359188, longitude: -2.1313584}, type: "church"},
        ]
    }

}

ReactDOM.render(<Router/>, document.getElementById("main"));