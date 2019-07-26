import BasePage from "../BasePage";
import * as ReactDOM from "react-dom";
import * as React from "react";

type State = {}

class PeoplePage extends BasePage<any, State> {

    active() {
        return "";
    }

    body() {
        return undefined;
    }

}

ReactDOM.render(<PeoplePage/>, document.getElementById("main"));