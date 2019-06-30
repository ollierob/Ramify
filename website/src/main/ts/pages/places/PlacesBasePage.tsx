import * as React from "react";
import * as ReactDOM from "react-dom";
import {HashRouter, Route, Switch} from "react-router-dom";
import BasePage from "../BasePage";
import PlacesHomePage from "./home/PlacesHomePage";

class PlacesBasePage extends BasePage {

    body() {
        return <HashRouter>
            <Switch>
                <Route exact path="" component={PlacesHomePage}/>
            </Switch>
        </HashRouter>;
    }

    active(): string {
        return "places";
    }

}

ReactDOM.render(<PlacesBasePage/>, document.getElementById("main"));