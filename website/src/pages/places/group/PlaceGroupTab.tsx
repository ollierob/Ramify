import * as React from "react";
import {PlaceBundle} from "../../../protobuf/generated/place_pb";
import {PlaceFavouritesHandler} from "../../../components/places/PlaceFavourites";
import {PlaceBreadcrumb} from "../info/PlaceBreadcrumb";
import {PlaceInfo} from "../info/PlaceInfo";
import {AsyncData, asyncLoadData} from "../../../components/fetch/AsyncData";
import {RecordSet} from "../../../protobuf/generated/record_pb";
import {PlaceId} from "../../../components/places/Place";
import {DEFAULT_RECORD_LOADER} from "../../../components/records/RecordLoader";

type Props = {
    place: PlaceBundle.AsObject;
    favourites: PlaceFavouritesHandler
}

type State = {
    records: AsyncData<ReadonlyArray<RecordSet.AsObject>>
}

export class PlaceGroupTab extends React.PureComponent<Props, State> {

    private readonly recordLoader = DEFAULT_RECORD_LOADER;

    constructor(props: Props) {
        super(props);
        this.state = {
            records: {}
        };
    }

    componentDidMount() {
        if (this.props.place) this.loadRecords(this.props.place.place.id);
    }

    componentDidUpdate(prevProps: Readonly<Props>, prevState: Readonly<State>) {
        if (this.props.place && this.props.place != prevProps.place)
            this.loadRecords(this.props.place.place.id);
    }

    render() {

        const place = this.props.place;
        if (!place) return null;

        return <>
            <PlaceBreadcrumb
                place={place.place}
                skipSelf/>
            <PlaceInfo
                {...this.props.favourites}
                card={false}
                place={place.place}
                description={place.description}
                childPlaces={place.childList}
                records={this.state.records}/>
        </>;

    }

    private loadRecords(place: PlaceId) {
        if (!place) return;
        asyncLoadData(place, id => this.recordLoader.loadRecordSets({place}), records => this.setState({records}));
    }

}