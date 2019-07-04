import * as React from "react";
import {RouteComponentProps} from "react-router";
import {Place} from "../../protobuf/generated/place_pb";
import {PlaceId} from "../../components/places/Place";
import {AsyncData, asyncLoadData} from "../../components/fetch/AsyncData";
import {DEFAULT_PLACE_LOADER} from "../../components/places/PlaceLoader";
import {PlaceBreadcrumb} from "./PlaceBreadcrumb";
import {PlacesPageProps} from "./PlacesBasePage";
import {Position} from "../../protobuf/generated/location_pb";

type Props = RouteComponentProps<any> & {
    childType: React.ComponentType<PlacesPageProps>;
}

type State = {
    placeId?: PlaceId;
    place: AsyncData<Place.AsObject>;
    position: AsyncData<Position.AsObject>
}

export default class PlacesBreadcrumbWrapper extends React.PureComponent<Props, State> {

    private readonly placeFetcher = DEFAULT_PLACE_LOADER;

    constructor(props: Props) {
        super(props);
        this.state = {
            place: {},
            position: {}
        }
    }

    render() {

        const Type = this.props.childType;

        return <>

            <PlaceBreadcrumb
                loading={this.state.place.loading}
                place={this.state.place.data}/>

            <div className="content">
                <Type
                    {...this.props}
                    placeId={this.state.placeId}
                    place={this.state.place.data}
                    position={this.state.position.data}
                    loading={this.state.place.loading || this.state.position.loading}/>
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
            id => this.placeFetcher.loadPlace(id),
            place => this.setState({place}));
        asyncLoadData(
            id,
            id => this.placeFetcher.loadPosition(id),
            position => this.setState({position}));
    }


}