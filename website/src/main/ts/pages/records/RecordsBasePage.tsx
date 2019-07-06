import BasePage from "../BasePage";
import * as ReactDOM from "react-dom";
import * as React from "react";

class RecordsBasePage extends BasePage {

    active() {
        return "records";
    }

    body() {
        return undefined;
    }

}
ReactDOM.render(<RecordsBasePage/>, document.getElementById("main"));