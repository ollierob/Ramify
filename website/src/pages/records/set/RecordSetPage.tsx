import * as React from "react";
import {AsyncData, asyncLoadData} from "../../../components/fetch/AsyncData";
import {RecordSearch, RecordSet, RecordSetRelatives} from "../../../protobuf/generated/record_pb";
import {RouteComponentProps} from "react-router";
import {DEFAULT_RECORD_LOADER, EnrichedRecord} from "../../../components/records/RecordLoader";
import {DEFAULT_PLACE_LOADER} from "../../../components/places/PlaceLoader";
import {PlaceBundle} from "../../../protobuf/generated/place_pb";
import {ErrorMessage} from "../../../components/style/Error";
import {PlaceId} from "../../../components/places/Place";
import {PlaceMap} from "../../../components/places/PlaceMap";
import RecordSetCard from "./RecordSetCard";
import {PlaceInfo} from "../../../components/places/PlaceInfo";
import BasePage from "../../BasePage";

type Props = RouteComponentProps<any>

type State = {
    records: AsyncData<ReadonlyArray<EnrichedRecord>>
    recordSetId?: string;
    recordSet: AsyncData<RecordSet.AsObject>;
    recordSetCoversPlace: AsyncData<PlaceBundle.AsObject>
    recordSetSource: AsyncData<PlaceBundle.AsObject>
    recordSetRelatives: AsyncData<RecordSetRelatives.AsObject>
    search: RecordSearch;
    searchResults: AsyncData<ReadonlyArray<EnrichedRecord>>;
}

export default class RecordSetPage extends BasePage<Props, State> {

    private readonly recordLoader = DEFAULT_RECORD_LOADER;
    private readonly placeLoader = DEFAULT_PLACE_LOADER;

    constructor(props) {
        super(props);
        this.state = {
            search: null, //TOOO read page hash
            records: {},
            recordSet: {},
            recordSetCoversPlace: {loading: true},
            recordSetSource: {loading: true},
            recordSetRelatives: {},
            searchResults: {}
        };
        this.search = this.search.bind(this);
    }

    active(): string {
        return "";
    }

    body() {

        const recordSet = this.state.recordSet.data;
        if (!recordSet && !this.state.recordSet.loading) return <ErrorMessage message="Unknown record set"/>;

        return <div className="content recordSet leftRest">

            <PlaceMap
                loading={this.state.recordSetCoversPlace.loading}
                place={this.state.recordSetCoversPlace.data && this.state.recordSetCoversPlace.data.place}
                position={this.state.recordSetCoversPlace.data && this.state.recordSetCoversPlace.data.position}/>

            <PlaceInfo
                preTitle={<>These records relate to<br/></>}
                loading={this.state.recordSetCoversPlace.loading}
                description={this.state.recordSetCoversPlace.data && this.state.recordSetCoversPlace.data.description}
                place={this.state.recordSetCoversPlace.data && this.state.recordSetCoversPlace.data.place}/>

            <RecordSetCard
                {...this.props}
                loading={this.state.recordSet.loading}
                recordSet={recordSet}
                recordSetRelatives={this.state.recordSetRelatives}
                records={this.state.records}
                paginate={null}
                search={this.state.search}
                searching={this.state.searchResults.loading}
                doSearch={this.search}
                searchResults={this.state.searchResults}/>

        </div>;

    }

    componentDidMount() {
        this.readLocation();
    }

    componentDidUpdate(prevProps: Readonly<Props>, prevState: Readonly<State>) {
        if (this.props.location != prevProps.location)
            this.readLocation();
        if (this.state.recordSetId != prevState.recordSetId)
            this.loadRecordSet(this.state.recordSetId);
        if (this.state.recordSet.data && this.state.recordSet.data != prevState.recordSet.data)
            this.loadPlace(this.state.recordSet.data.coversplaceid, this.state.recordSet.data.creatorplaceid);
    }

    private readLocation() {
        const location = this.props.location;
        if (!location) return;
        const search = new URLSearchParams(location.search);
        this.setState({recordSetId: search.get("id")});
    }

    private loadRecordSet(id: string) {
        if (!id) return;
        asyncLoadData(id, this.recordLoader.loadRecordSet, recordSet => this.setState({recordSet}));
        asyncLoadData(id, this.recordLoader.loadRecordSetRelatives, recordSetRelatives => this.setState({recordSetRelatives}));
        asyncLoadData(id, id => this.recordLoader.loadRecords(id, {children: true, limit: 100}), records => this.setState({records}));
    }

    private loadPlace(covers: PlaceId, source: PlaceId) {
        if (covers) asyncLoadData(covers, this.placeLoader.loadPlaceBundle, place => this.setState({recordSetCoversPlace: place}));
        else this.setState({recordSetCoversPlace: {}});
        if (source) asyncLoadData(source, this.placeLoader.loadPlaceBundle, place => this.setState({recordSetSource: place}));
        else this.setState({recordSetSource: {}});
    }

    private search(search: RecordSearch) {
        if (this.state.recordSetId) search.setRecordsetid(this.state.recordSetId);
        asyncLoadData(search, this.recordLoader.submitSearch, searchResults => this.setState({searchResults}));
    }

}