import * as React from "react";
import {AsyncData, asyncLoadData} from "../../../components/fetch/AsyncData";
import {Record, RecordSearch, RecordSet} from "../../../protobuf/generated/record_pb";
import {RouteComponentProps} from "react-router";
import {DEFAULT_RECORD_LOADER} from "../../../components/records/RecordLoader";
import {Loading} from "../../../components/style/Loading";
import {DEFAULT_PLACE_LOADER} from "../../../components/places/PlaceLoader";
import {PlaceBundle} from "../../../protobuf/generated/place_pb";
import {ErrorMessage} from "../../../components/style/Error";
import {PlaceId} from "../../../components/places/Place";
import {PlaceMap} from "../../../components/places/PlaceMap";
import RecordSetCard from "./RecordSetCard";
import {PlaceInfo} from "../../../components/places/PlaceInfo";

type Props = RouteComponentProps<any>

type State = {
    records: AsyncData<ReadonlyArray<Record.AsObject>>
    recordSetId?: string;
    recordSet: AsyncData<RecordSet.AsObject>;
    recordSetCoversPlace: AsyncData<PlaceBundle.AsObject>
    recordSetSource: AsyncData<PlaceBundle.AsObject>
    recordSetChildren: AsyncData<ReadonlyArray<RecordSet.AsObject>>
    search: RecordSearch;
    searchResults: AsyncData<ReadonlyArray<Record.AsObject>>;
}

export default class RecordSetPage extends React.PureComponent<Props, State> {

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
            recordSetChildren: {},
            searchResults: {}
        };
        this.search = this.search.bind(this);
    }

    render() {

        if (this.state.recordSet.loading) return <Loading/>;

        const recordSet = this.state.recordSet.data;
        if (!recordSet) return <ErrorMessage message="Unknown record set"/>;

        //TODO place map should show both source and coverage of record set
        return <div className="recordSet leftRest">

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
                recordSet={recordSet}
                recordSetChildren={this.state.recordSetChildren}
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
        asyncLoadData(id, this.recordLoader.loadChildRecordSets, children => this.setState({recordSetChildren: children}));
        asyncLoadData(id, this.recordLoader.loadRecords, records => this.setState({records}));
    }

    private loadPlace(covers: PlaceId, source: PlaceId) {
        if (covers) asyncLoadData(covers, this.placeLoader.loadPlaceBundle, place => this.setState({recordSetCoversPlace: place}));
        else this.setState({recordSetCoversPlace: {}});
        if (source) asyncLoadData(source, this.placeLoader.loadPlaceBundle, place => this.setState({recordSetSource: place}));
        else this.setState({recordSetSource: {}});
    }

    private search(search: RecordSearch) {
        if (this.state.recordSetId) search.setRecordid(this.state.recordSetId);
        asyncLoadData(search, this.recordLoader.submitSearch, searchResults => this.setState({searchResults}));
    }

}