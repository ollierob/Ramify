import * as React from "react";
import * as ReactDOM from "react-dom";
import {HashRouter, Route, RouteComponentProps, Switch} from "react-router-dom";
import BasePage from "../BasePage";
import PlacesHomePage from "./home/PlacesHomePage";
import ChurchPage from "./institution/ChurchPage";
import PlacesBreadcrumbWrapper from "./PlacesBreadcrumbWrapper";
import "./Places.css";
import {PlaceId, PlaceList} from "../../components/places/Place";
import {Place, PlaceBundle} from "../../protobuf/generated/place_pb";
import AreaPage from "./area/AreaPage";
import SchoolPage from "./institution/SchoolPage";
import {addPlaceFavourite, getPlaceFavourites, PlaceFavouritesHandler, removePlaceFavourite} from "../../components/places/PlaceFavourites";
import {addPlaceHistory, getPlaceHistory} from "../../components/places/PlaceHistory";
import BuildingPage from "./building/BuildingPage";

type State = {
    history: PlaceList;
    favourites: PlaceList;
}

class PlacesPage extends BasePage<any, State> {

    constructor(props) {
        super(props);
        this.state = {
            history: getPlaceHistory(),
            favourites: getPlaceFavourites()
        };
        this.addPlaceFavourite = this.addPlaceFavourite.bind(this);
        this.removePlaceFavourite = this.removePlaceFavourite.bind(this);
        this.addPlaceHistory = this.addPlaceHistory.bind(this);
    }


    active(): string {
        return "places";
    }

    body() {
        return <HashRouter>
            <Switch>
                <Route path="/church" component={this.breadcrumb(ChurchPage)}/>
                <Route path="/school" component={this.breadcrumb(SchoolPage)}/>
                <Route path="/farmstead" component={this.breadcrumb(BuildingPage)}/>
                <Route exact path="/" component={this.breadcrumb(PlacesHomePage, true)}/>
                <Route path="*" component={this.breadcrumb(AreaPage)}/>
            </Switch>
        </HashRouter>;
    }

    placeFavourites() {
        return this.state.favourites;
    }

    placeHistory() {
        return this.state.history;
    }

    private breadcrumb(type: React.ComponentType<PlacesPageProps>, hideMenu?: boolean): React.ComponentType<RouteComponentProps<any>> {
        return params => <PlacesBreadcrumbWrapper
            {...params}
            key="placeBreadcrumb"
            childType={type}
            hideMenu={hideMenu}
            placeHistory={this.state.history}
            addPlaceHistory={this.addPlaceHistory}
            placeFavourites={this.state.favourites}
            addPlaceFavourite={this.addPlaceFavourite}
            removePlaceFavourite={this.removePlaceFavourite}/>;
    }

    private addPlaceHistory(place: Place.AsObject) {
        //if (place) this.setState({history: addPlaceHistory(place)}) //FIXME stop this remounting on state change
        addPlaceHistory(place);
    }

    private addPlaceFavourite(place: Place.AsObject) {
        if (place) this.setState({favourites: addPlaceFavourite(place)});
    }

    private removePlaceFavourite(place: Place.AsObject) {
        if (place) this.setState({favourites: removePlaceFavourite(place.id)});
    }

}

export type PlacesPageProps = RouteComponentProps<any> & PlaceFavouritesHandler & {
    placeId: PlaceId;
    place: PlaceBundle.AsObject;
    loading: boolean;
    placeHistory: PlaceList;
}

ReactDOM.render(<PlacesPage/>, document.getElementById("main"));