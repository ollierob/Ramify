import * as React from "react";
import {PlacesPageProps} from "../PlacesPage";
import {PlaceMap} from "../../../components/places/PlaceMap";
import {PlaceInfo} from "./PlaceInfo";
import {AsyncData, asyncLoadData} from "../../../components/fetch/AsyncData";
import {Place} from "../../../protobuf/generated/place_pb";
import {DEFAULT_PLACE_LOADER} from "../../../components/places/PlaceLoader";
import {PlaceTypeDescription} from "../../../components/places/PlaceTypeDescription";
import {Loading} from "../../../components/style/Loading";
import {DEFAULT_RECORD_LOADER} from "../../../components/records/RecordLoader";
import {RecordSet} from "../../../protobuf/generated/record_pb";

type Props = PlacesPageProps;

type State = {
    children: AsyncData<ReadonlyArray<Place.AsObject>>
    placeRecords: AsyncData<ReadonlyArray<RecordSet.AsObject>>
}

export default class AreaPage extends React.PureComponent<Props, State> {

    private readonly placeLoader = DEFAULT_PLACE_LOADER;
    private readonly recordLoader = DEFAULT_RECORD_LOADER;

    constructor(props: Props) {
        super(props);
        this.state = {
            children: {},
            placeRecords: {}
        };
    }

    render() {

        if (this.props.loading) return <Loading/>;

        const bundle = this.props.place;
        if (!bundle) return null;

        return <div className="area leftRest">

            <PlaceMap
                area={true}
                loading={this.props.loading}
                place={bundle.place}
                position={bundle.position}/>

            <PlaceTypeDescription
                description={bundle.typedescription}/>

            <PlaceInfo
                {...this.props}
                loadingChildren={this.state.children.loading}
                childPlaces={this.state.children.data}
                place={bundle.place}
                description={bundle.description}
                records={this.state.placeRecords}/>

        </div>;

    }

    componentDidMount() {
        this.loadChildren();
    }

    componentDidUpdate(prevProps: Props) {

        if (this.props.placeId != prevProps.placeId) {
            this.loadChildren(this.props.placeId);
            this.loadRecords(this.props.placeId);
        }

    }

    private loadChildren(id = this.props.placeId) {
        if (!id) return;
        asyncLoadData(
            id,
            id => this.placeLoader.loadChildren(id), //Let server determine best max depth
            children => this.setState({children}));
    }

    private loadRecords(id = this.props.placeId) {
        if (!id) return;
        asyncLoadData(
            id,
            id => this.recordLoader.loadRecordSets({place: id, onlyParents: true}),
            placeRecords => this.setState({placeRecords}));
    }

}