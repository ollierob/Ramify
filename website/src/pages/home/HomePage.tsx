import * as React from "react";
import * as ReactDOM from "react-dom";
import BasePage from "../BasePage";
import "./HomePage.css"

class HomePage extends BasePage {

    active() {
        return null;
    }

    body() {
        return null;
    }

}

ReactDOM.render(<HomePage/>, document.getElementById("main"));