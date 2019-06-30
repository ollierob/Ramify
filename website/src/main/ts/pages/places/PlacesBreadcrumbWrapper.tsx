import * as React from "react";
import {RouteComponentProps} from "react-router";
import {Place} from "../../protobuf/generated/place_pb";
import {PlaceId} from "../../components/places/Place";
import {AsyncData, asyncLoadData} from "../../components/fetch/AsyncData";
import {DEFAULT_PLACE_FETCHER} from "../../components/places/PlaceFetcher";
import {PlaceBreadcrumb} from "./PlaceBreadcrumb";

type Props = RouteComponentProps<any> & {
    childType: React.ComponentType<RouteComponentProps<any>>;
}

type State = {
    placeId?: PlaceId;
    place: AsyncData<Place.AsObject>
}

export default class PlacesBreadcrumbWrapper extends React.PureComponent<Props, State> {

    private readonly placeFetcher = DEFAULT_PLACE_FETCHER;

    constructor(props: Props) {
        super(props);
        this.state = {
            place: {}
        }
    }

    render() {

        const Type = this.props.childType;

        return <>

            <PlaceBreadcrumb
                loading={this.state.place.loading}
                place={this.state.place.data}/>

            <Type {...this.props}/>

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

    }

    private readPlace() {
        const location = this.props.location;
        if (!location) return;
        const search = new URLSearchParams(location.search);
        this.setState({
            placeId: search.get("place")
        })
    }

    private loadPlace(id: PlaceId) {
        if (!id) return;
        asyncLoadData(
            id,
            id => this.placeFetcher.fetchPlace(id),
            place => this.setState({place}))
    }

}