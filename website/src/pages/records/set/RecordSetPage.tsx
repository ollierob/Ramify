import * as React from "react";
import {AsyncData, asyncLoadData} from "../../../components/fetch/AsyncData";
import {RecordSet} from "../../../protobuf/generated/record_pb";
import {RouteComponentProps} from "react-router";
import {DEFAULT_RECORD_LOADER} from "../../../components/records/RecordLoader";
import {Loading} from "../../../components/Loading";
import {DEFAULT_PLACE_LOADER} from "../../../components/places/PlaceLoader";
import {PlaceBundle} from "../../../protobuf/generated/place_pb";
import {ErrorMessage} from "../../../components/style/Error";
import {PlaceId} from "../../../components/places/Place";
import {PlaceMap} from "../../../components/places/PlaceMap";
import RecordSetCard from "./RecordSetCard";

type Props = RouteComponentProps<any>

type State = {
    recordSetId?: string;
    recordSet: AsyncData<RecordSet.AsObject>;
    recordSetPlace: AsyncData<PlaceBundle.AsObject>
    recordSetChildren: AsyncData<ReadonlyArray<RecordSet.AsObject>>
}

export default class RecordSetPage extends React.PureComponent<Props, State> {

    private readonly recordLoader = DEFAULT_RECORD_LOADER;
    private readonly placeLoader = DEFAULT_PLACE_LOADER;

    constructor(props) {
        super(props);
        this.state = {
            recordSet: {},
            recordSetPlace: {loading: true},
            recordSetChildren: {}
        };
    }

    render() {

        if (this.state.recordSet.loading) return <Loading/>;

        const data = this.state.recordSet.data;
        if (!data) return <ErrorMessage message="Unknown record set"/>;

        return <div className="recordSet leftRest">

            <PlaceMap
                loading={this.state.recordSetPlace.loading}
                place={this.state.recordSetPlace.data && this.state.recordSetPlace.data.place}
                position={this.state.recordSetPlace.data && this.state.recordSetPlace.data.position}/>

            <RecordSetCard
                recordSet={data}
                recordSetChildren={this.state.recordSetChildren.data}/>

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
            this.loadPlace(this.state.recordSet.data.placeid);
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
    }

    private loadPlace(id: PlaceId) {
        if (!id) return this.setState({recordSetPlace: {}});
        asyncLoadData(id, this.placeLoader.loadPlaceBundle, place => this.setState({recordSetPlace: place}));
    }

}