import * as React from "react";
import {RouteComponentProps} from "react-router";
import {PlaceBundle} from "../../protobuf/generated/place_pb";
import {PlaceId} from "../../components/places/Place";
import {AsyncData, asyncLoadData} from "../../components/fetch/AsyncData";
import {DEFAULT_PLACE_LOADER} from "../../components/places/PlaceLoader";
import {PlaceBreadcrumb} from "./PlaceBreadcrumb";
import {PlacesPageProps} from "./PlacesPage";
import {PlaceHistoryHandler} from "../../components/places/PlaceHistory";
import {PlaceFavouritesHandler} from "../../components/places/PlaceFavourites";

type Props = RouteComponentProps<any> & PlaceFavouritesHandler & PlaceHistoryHandler & {
    hideMenu?: boolean;
    childType: React.ComponentType<PlacesPageProps>;
}

type State = {
    placeId?: PlaceId;
    place: AsyncData<PlaceBundle.AsObject>;
}

export default class PlacesBreadcrumbWrapper extends React.PureComponent<Props, State> {

    private readonly placeFetcher = DEFAULT_PLACE_LOADER;

    constructor(props: Props) {
        super(props);
        this.state = {
            place: {},
        }
    }

    render() {

        const Type = this.props.childType;
        const bundle = this.state.place.data;

        return <>

            {!this.props.hideMenu && <PlaceBreadcrumb
                loading={this.state.place.loading}
                place={bundle && bundle.place}/>}

            <div className="content">
                <Type
                    {...this.props}
                    placeId={this.state.placeId}
                    place={bundle}
                    loading={this.state.place.loading}/>
            </div>

        </>

    }

    componentDidMount() {
        this.readPlace();
    }

    componentDidUpdate(prevProps: Readonly<Props>, prevState: Readonly<State>) {

        if (this.props.location != prevProps.location)
            this.readPlace();

        if (this.state.placeId != prevState.placeId)
            this.loadPlace(this.state.placeId);

        if (this.state.place.data && this.state.place != prevState.place)
            this.props.addPlaceHistory(this.state.place.data.place);

    }

    private readPlace() {
        const location = this.props.location;
        if (!location) return;
        const search = new URLSearchParams(location.search);
        this.setState({placeId: search.get("place")})
    }

    private loadPlace(id: PlaceId) {
        if (!id) return;
        asyncLoadData(
            id,
            id => this.placeFetcher.loadPlaceBundle(id),
            place => this.setState({place}));
    }


}