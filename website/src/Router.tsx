import * as React from "react";
import * as ReactDOM from "react-dom";
import MapContainer from "./components/MapContainer";

export default class Router extends React.Component {

    render() {
        return <MapContainer zoom={13.5} center={{latitude: 53.74, longitude: -2.045}}/>
    }

}

ReactDOM.render(<Router/>, document.getElementById("main"));