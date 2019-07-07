import * as React from "react";
import * as ReactDOM from "react-dom";
import {HashRouter, Route, RouteComponentProps, Switch} from "react-router-dom";
import BasePage from "../BasePage";
import PlacesHomePage from "./home/PlacesHomePage";
import ChurchPage from "./institution/ChurchPage";
import PlacesBreadcrumbWrapper from "./PlacesBreadcrumbWrapper";
import "./Places.css"
import {PlaceId, PlaceList} from "../../components/places/Place";
import {Place, PlaceBundle} from "../../protobuf/generated/place_pb";
import AreaPage from "./area/AreaPage";
import SchoolPage from "./institution/SchoolPage";
import {addPlaceFavourite, getPlaceFavourites, PlaceFavouritesHandler, removePlaceFavourite} from "../../components/places/PlaceFavourites";
import {getPlaceHistory} from "../../components/places/PlaceHistory";

type State = {
    history: PlaceList;
    favourites: PlaceList;
}

class PlacesBasePage extends BasePage<State> {

    constructor(props) {
        super(props);
        this.state = {
            history: getPlaceHistory(),
            favourites: getPlaceFavourites()
        };
        this.addPlaceFavourite = this.addPlaceFavourite.bind(this);
        this.removePlaceFavourite = this.removePlaceFavourite.bind(this);
    }


    active(): string {
        return "places";
    }

    body() {
        return <HashRouter>
            <Switch>
                <Route path="/church" component={this.breadcrumb(ChurchPage)}/>
                <Route path="/school" component={this.breadcrumb(SchoolPage)}/>
                <Route exact path="/" component={this.breadcrumb(PlacesHomePage)}/>
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

    private breadcrumb(type: React.ComponentType<PlacesPageProps>): React.ComponentType<RouteComponentProps<any>> {
        return params => <PlacesBreadcrumbWrapper
            {...params}
            childType={type}
            placeHistory={this.state.history}
            placeFavourites={this.state.favourites}
            addPlaceFavourite={this.addPlaceFavourite}
            removePlaceFavourite={this.removePlaceFavourite}/>
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

ReactDOM.render(<PlacesBasePage/>, document.getElementById("main"));