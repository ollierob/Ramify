import BasePage from "../BasePage";
import * as ReactDOM from "react-dom";
import * as React from "react";

class RecordsPage extends BasePage {

    active() {
        return "records";
    }

    body() {
        return undefined;
    }

}

ReactDOM.render(<RecordsPage/>, document.getElementById("main"));