import * as React from "react";
import * as ReactDOM from "react-dom";
import {HashRouter, Route, RouteComponentProps, Switch} from "react-router-dom";
import BasePage from "../BasePage";
import PlacesHomePage from "./home/PlacesHomePage";
import ChurchPage from "./institution/ChurchPage";
import PlacesBreadcrumbWrapper from "./PlacesBreadcrumbWrapper";
import "./Places.css"
import {PlaceId} from "../../components/places/Place";
import {Place} from "../../protobuf/generated/place_pb";
import AreaPage from "./area/AreaPage";
import {Position} from "../../protobuf/generated/location_pb";

class PlacesBasePage extends BasePage {

    body() {
        return <HashRouter>
            <Switch>
                <Route path="/church" component={breadcrumb(ChurchPage)}/>
                <Route exact path="/" component={breadcrumb(PlacesHomePage)}/>
                <Route path="*" component={breadcrumb(AreaPage)}/>
            </Switch>
        </HashRouter>;
    }

    active(): string {
        return "places";
    }

}

function breadcrumb(type: React.ComponentType<PlacesPageProps>): React.ComponentType<RouteComponentProps<any>> {
    return params => <PlacesBreadcrumbWrapper {...params} childType={type}/>
}

export type PlacesPageProps = RouteComponentProps<any> & {
    placeId: PlaceId;
    place: Place.AsObject;
    position: Position.AsObject;
    loading: boolean
}

ReactDOM.render(<PlacesBasePage/>, document.getElementById("main"));