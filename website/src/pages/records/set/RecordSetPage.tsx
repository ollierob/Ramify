import * as React from "react";
import {AsyncData, asyncLoadData, mapAsyncData} from "../../../components/fetch/AsyncData";
import {RecordSearch, RecordSet, RecordSetHierarchy} from "../../../protobuf/generated/record_pb";
import {DEFAULT_RECORD_LOADER, EnrichedIndividualRecord} from "../../../components/records/RecordLoader";
import {DEFAULT_PLACE_LOADER} from "../../../components/places/PlaceLoader";
import {PlaceBundle} from "../../../protobuf/generated/place_pb";
import {ErrorMessage} from "../../../components/style/Error";
import {PlaceId} from "../../../components/places/Place";
import {PlaceMap} from "../../../components/places/PlaceMap";
import RecordSetCard from "./RecordSetCard";
import {RecordPlaceInfo} from "./RecordPlaceInfo";
import {RecordBasePage, RecordBasePageProps} from "../RecordBasePage";
import {RecordSetId} from "../../../components/records/RecordSet";
import {RecordBreadcrumb} from "./RecordBreadcrumb";

type Props = RecordBasePageProps;

type State = {
    recordSetId?: string;
    recordSet: AsyncData<RecordSet.AsObject>;
    recordSetCoversPlace: AsyncData<PlaceBundle.AsObject>
    recordSetSource: AsyncData<PlaceBundle.AsObject>
    recordSetHierarchy?: AsyncData<RecordSetHierarchy.AsObject>
    search: RecordSearch;
    searchResults: AsyncData<ReadonlyArray<EnrichedIndividualRecord>>;
}

export default class RecordSetPage extends RecordBasePage<State> {

    private readonly recordLoader = DEFAULT_RECORD_LOADER;
    private readonly placeLoader = DEFAULT_PLACE_LOADER;

    constructor(props: Props) {
        super(props);
        this.state = {
            search: null, //TOOO read page hash
            recordSet: {},
            recordSetId: this.readLocation(),
            recordSetCoversPlace: {loading: true},
            recordSetSource: {loading: true},
            recordSetHierarchy: {loading: true},
            searchResults: {}
        };
    }

    body() {

        const recordSet = this.state.recordSet.data;
        if (!recordSet && !this.state.recordSet.loading) return <ErrorMessage message="Unknown record set" className="content"/>;

        return <>

            <RecordBreadcrumb
                hierarchy={this.state.recordSetHierarchy.data}/>

            <div className="content recordSet leftRest">

                <PlaceMap
                    loading={this.state.recordSetCoversPlace.loading}
                    places={[this.state.recordSetCoversPlace.data, this.state.recordSetSource.data && this.state.recordSetSource.data]}/>

                <RecordPlaceInfo
                    preTitle={<>These records relate to<br/></>}
                    loading={this.state.recordSetCoversPlace.loading}
                    description={this.state.recordSetCoversPlace.data && this.state.recordSetCoversPlace.data.description}
                    place={this.state.recordSetCoversPlace.data && this.state.recordSetCoversPlace.data.place}/>

                <RecordSetCard
                    {...this.props}
                    loading={this.state.recordSet.loading}
                    recordSet={recordSet}
                    relatives={mapAsyncData(this.state.recordSetHierarchy, h => h.self)}
                    paginate={null}
                    creatorPlace={this.state.recordSetSource}/>

            </div>

        </>;

    }

    componentDidMount() {
        this.loadRecordSet();
    }

    componentDidUpdate(prevProps: Readonly<Props>, prevState: Readonly<State>) {
        if (this.props.location != prevProps.location)
            this.setState({recordSetId: this.readLocation()});
        if (this.state.recordSetId != prevState.recordSetId)
            this.loadRecordSet(this.state.recordSetId);
        if (this.state.recordSet.data && this.state.recordSet.data != prevState.recordSet.data) {
            this.loadPlace(this.state.recordSet.data.coversplaceid, this.state.recordSet.data.creatorplaceid);
            this.setPageTitle(this.state.recordSet.data.longtitle);
        }
    }

    private readLocation(): RecordSetId {
        return this.urlParameter("id");
    }

    private loadRecordSet(id: string = this.state.recordSetId) {
        if (!id) return;
        asyncLoadData(id, this.recordLoader.loadRecordSet, recordSet => this.setState({recordSet}));
        asyncLoadData(id, this.recordLoader.loadRecordSetHierarchy, recordSetHierarchy => this.setState({recordSetHierarchy}));
    }

    private loadPlace(covers: PlaceId, source: PlaceId) {
        if (covers) asyncLoadData(covers, this.placeLoader.loadPlaceBundle, place => this.setState({recordSetCoversPlace: place}));
        else this.setState({recordSetCoversPlace: {}});
        if (source) asyncLoadData(source, this.placeLoader.loadPlaceBundle, place => this.setState({recordSetSource: place}));
        else this.setState({recordSetSource: {}});
    }

}