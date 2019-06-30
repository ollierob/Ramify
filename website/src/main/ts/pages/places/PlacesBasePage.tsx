import * as React from "react";
import * as ReactDOM from "react-dom";
import {HashRouter, Route, RouteComponentProps, Switch} from "react-router-dom";
import BasePage from "../BasePage";
import PlacesHomePage from "./home/PlacesHomePage";
import ChurchesPage from "./churches/ChurchesPage";
import PlacesBreadcrumbWrapper from "./PlacesBreadcrumbWrapper";
import "./Places.css"
import GeneralPlacePage from "./general/GeneralPlacePage";

class PlacesBasePage extends BasePage {

    body() {
        return <HashRouter>
            <Switch>
                <Route exact path="/" component={breadcrumb(PlacesHomePage)}/>
                <Route path="/?place=church:" component={breadcrumb(ChurchesPage)}/>
                <Route path="*" component={breadcrumb(GeneralPlacePage)}/>
            </Switch>
        </HashRouter>;
    }

    active(): string {
        return "places";
    }

}

function breadcrumb(type: React.ComponentType<RouteComponentProps<any>>): React.ComponentType<RouteComponentProps<any>> {
    return params => <PlacesBreadcrumbWrapper {...params} childType={type}/>
}

ReactDOM.render(<PlacesBasePage/>, document.getElementById("main"));