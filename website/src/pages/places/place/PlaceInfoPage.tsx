import * as React from "react";
import {PlaceMap} from "../../../components/places/PlaceMap";
import {PlaceInfo} from "./PlaceInfo";
import {AsyncData, asyncLoadData} from "../../../components/fetch/AsyncData";
import {Place, PlaceBundle} from "../../../protobuf/generated/place_pb";
import {DEFAULT_PLACE_LOADER} from "../../../components/places/PlaceLoader";
import {PlaceTypeDescription} from "../../../components/places/PlaceTypeDescription";
import {Loading} from "../../../components/style/Loading";
import {DEFAULT_RECORD_LOADER} from "../../../components/records/RecordLoader";
import {RecordSet} from "../../../protobuf/generated/record_pb";
import {PlaceBasePage, PlaceBasePageProps} from "../PlaceBasePage";
import {PlaceId} from "../../../components/places/Place";
import {PlaceBreadcrumb} from "../PlaceBreadcrumb";
import "./PlaceInfo.css";

type Props = PlaceBasePageProps;

type State = {
    placeId: PlaceId;
    place: AsyncData<PlaceBundle.AsObject>;
    children: AsyncData<ReadonlyArray<Place.AsObject>>
    placeRecords: AsyncData<ReadonlyArray<RecordSet.AsObject>>
}

export default class PlaceInfoPage extends PlaceBasePage<State> {

    private readonly placeLoader = DEFAULT_PLACE_LOADER;
    private readonly recordLoader = DEFAULT_RECORD_LOADER;

    constructor(props: Props) {
        super(props);
        this.state = {
            children: {},
            place: {loading: true},
            placeRecords: {},
            placeId: this.readPlace()
        };
    }

    body() {

        if (this.state.place.loading) return <Loading/>;

        const bundle = this.state.place.data;
        if (!bundle) return null;

        return <>

            <PlaceBreadcrumb
                loading={this.state.place.loading}
                place={bundle.place}/>

            <div className="content area leftRest">

                <PlaceMap
                    area={true}
                    loading={this.state.place.loading}
                    place={bundle.place}
                    position={bundle.position}/>

                <PlaceTypeDescription
                    description={bundle.typedescription}/>

                <PlaceInfo
                    {...this.favouritesHandler}
                    loadingChildren={this.state.children.loading}
                    childPlaces={this.state.children.data}
                    place={bundle.place}
                    description={bundle.description}
                    records={this.state.placeRecords}/>

            </div>

        </>;

    }

    componentDidMount() {
        this.loadPlace();
        this.loadChildren();
        this.loadRecords();
    }

    componentDidUpdate(prevProps: Readonly<Props>, prevState: Readonly<State>) {

        if (this.props.location != prevProps.location)
            this.setState({placeId: this.readPlace()});

        if (this.state.placeId != prevState.placeId) {
            this.loadPlace();
            this.loadChildren();
            this.loadRecords();
        }

    }

    private readPlace(): PlaceId {
        return this.urlParameter("place");
    }

    private loadPlace(id = this.state.placeId) {
        if (!id) return;
        asyncLoadData(
            id,
            this.placeLoader.loadPlaceBundle,
            place => this.setState({place}));
    }

    private loadChildren(id = this.state.placeId) {
        if (!id) return;
        asyncLoadData(
            id,
            id => this.placeLoader.loadChildren(id), //Let server determine best max depth
            children => this.setState({children}));
    }

    private loadRecords(id = this.state.placeId) {
        if (!id) return;
        asyncLoadData(
            id,
            id => this.recordLoader.loadRecordSets({place: id, onlyParents: true}),
            placeRecords => this.setState({placeRecords}));
    }

}