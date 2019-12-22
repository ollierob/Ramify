import * as React from "react";
import * as ReactDOM from "react-dom";
import {HashRouter, Route, Switch} from "react-router-dom";
import PlacesHomePage from "./home/PlacesHomePage";
import {PlaceGroupPage} from "./group/PlaceGroupPage";
import "./Places.css";

class PlacesRouter extends React.PureComponent {

    render() {
        return <HashRouter>
            <Switch>
                <Route exact path="/" component={PlacesHomePage}/>
                <Route path="/group" component={PlaceGroupPage}/>
            </Switch>
        </HashRouter>;
    }

}

ReactDOM.render(<PlacesRouter/>, document.getElementById("main"));