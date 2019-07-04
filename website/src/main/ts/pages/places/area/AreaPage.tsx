import * as React from "react";
import {PlacesPageProps} from "../PlacesBasePage";
import {PlaceMap} from "../../../components/places/PlaceMap";
import {AreaInfo} from "./AreaInfo";
import {AsyncData, asyncLoadData} from "../../../components/fetch/AsyncData";
import {Place} from "../../../protobuf/generated/place_pb";
import {DEFAULT_PLACE_LOADER} from "../../../components/places/PlaceLoader";

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

        if (!this.props.place) return null;

        const bundle = this.props.place;

        return <div className="town leftRest">

            <PlaceMap
                area={true}
                loading={this.props.loading}
                place={bundle && bundle.place}
                position={bundle && bundle.position}
                zoom={10}/>

            <AreaInfo
                loadingChildren={this.state.children.loading}
                childPlaces={this.state.children.data}
                place={bundle && bundle.place}
                description={bundle && bundle.description}/>

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
        asyncLoadData(id, id => this.placeLoader.loadChildren(id, 3), children => this.setState({children}));
    }
}

