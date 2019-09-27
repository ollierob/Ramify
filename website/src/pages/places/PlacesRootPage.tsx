import * as React from "react";
import * as ReactDOM from "react-dom";
import {HashRouter, Route, Switch} from "react-router-dom";
import PlacesHomePage from "./home/PlacesHomePage";
import "./Places.css";
import PlaceInfoPage from "./place/PlaceInfoPage";

class PlacesRootPage extends React.PureComponent {

    render() {
        return <HashRouter>
            <Switch>
                <Route exact path="/" component={PlacesHomePage}/>
                <Route path="*" component={PlaceInfoPage}/>
            </Switch>
        </HashRouter>;
    }

}

ReactDOM.render(<PlacesRootPage/>, document.getElementById("main"));