import * as React from "react";
import * as ReactDOM from "react-dom";
import {SlippyMap} from "react-slippy-map";

export default class Router extends React.Component {

    render() {
        return <SlippyMap zoom={12}/>
    }

}

ReactDOM.render(<Router/>, document.getElementById("main"));