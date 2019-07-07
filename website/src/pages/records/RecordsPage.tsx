import BasePage from "../BasePage";
import * as ReactDOM from "react-dom";
import * as React from "react";
import {HashRouter, Route, Switch} from "react-router-dom";
import RecordsHomePage from "./home/RecordsHomePage";
import "./Records.css"

class RecordsPage extends BasePage {

    active() {
        return "records";
    }

    body() {
        return <div className="content">
            <HashRouter>
                <Switch>
                    <Route exact path="/" component={RecordsHomePage}/>
                </Switch>
            </HashRouter>
        </div>;
    }

}

ReactDOM.render(<RecordsPage/>, document.getElementById("main"));