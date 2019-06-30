import * as React from "react";
import * as ReactDOM from "react-dom";
import {HashRouter, Route, Switch} from "react-router-dom";
import BasePage from "../BasePage";
import PlacesHomePage from "./home/PlacesHomePage";
import ChurchesPage from "./churches/ChurchesPage";

class PlacesBasePage extends BasePage {

    body() {
        return <HashRouter>
            <Switch>
                <Route exact path="/" component={PlacesHomePage}/>
                <Route path="/churches" component={ChurchesPage}/>
            </Switch>
        </HashRouter>;
    }

    active(): string {
        return "places";
    }

}

ReactDOM.render(<PlacesBasePage/>, document.getElementById("main"));