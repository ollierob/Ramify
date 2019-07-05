import * as React from "react";
import {PlacesPageProps} from "../PlacesBasePage";
import {PlaceMap} from "../../../components/places/PlaceMap";
import {AreaInfo} from "./AreaInfo";
import {AsyncData, asyncLoadData} from "../../../components/fetch/AsyncData";
import {Place, PlaceType} from "../../../protobuf/generated/place_pb";
import {DEFAULT_PLACE_LOADER} from "../../../components/places/PlaceLoader";
import {PlaceTypeDescription} from "../../../components/places/PlaceTypeDescription";
import {Loading} from "../../../components/Loading";

type Props = PlacesPageProps;

type State = {
    children: AsyncData<ReadonlyArray<Place.AsObject>>
}

export default class AreaPage extends React.PureComponent<Props, State> {

    private readonly placeLoader = DEFAULT_PLACE_LOADER;

    constructor(props: Props) {
        super(props);
        this.state = {
            children: {}
        }
    }

    render() {

        if (this.props.loading) return <Loading/>;

        const bundle = this.props.place;
        if (!bundle) return null;

        return <div className="town leftRest">

            <PlaceMap
                area={true}
                loading={this.props.loading}
                place={bundle.place}
                position={bundle.position}
                zoom={zoom(bundle.place)}/>

            <PlaceTypeDescription
                description={bundle.typedescription}/>

            <AreaInfo
                loadingChildren={this.state.children.loading}
                childPlaces={this.state.children.data}
                place={bundle.place}
                description={bundle.description}/>

        </div>;

    }

    componentDidMount() {
        this.loadChildren();
    }

    componentDidUpdate(prevProps: Props) {

        if (this.props.placeId != prevProps.placeId)
            this.loadChildren(this.props.placeId);

    }

    private loadChildren(id = this.props.placeId) {
        if (!id) return;
        asyncLoadData(
            id,
            id => this.placeLoader.loadChildren(id), //Let server determine best max depth
            children => this.setState({children}));
    }

}

function zoom(place: Place.AsObject): number {
    switch (place.type) {
        case PlaceType.PARISH:
        case PlaceType.CHAPELRY:
            return 11;
        case PlaceType.TOWNSHIP:
            return 12;
        case PlaceType.TOWN:
            return 13;
        case PlaceType.VILLAGE:
        case PlaceType.HAMLET:
            return 14;
        default:
            return 10;
    }
}