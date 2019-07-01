import * as React from "react";
import {DefaultChurchFetcher} from "../../../components/places/ChurchesFetcher";
import {PlacesPageProps} from "../PlacesBasePage";
import {AsyncData, asyncLoadData} from "../../../components/fetch/AsyncData";
import {Church} from "../../../protobuf/generated/church_pb";
import {DateRange} from "../../../components/date/DateRange";

type Props = PlacesPageProps

type State = {
    church: AsyncData<Church.AsObject>
}

export default class ChurchPage extends React.PureComponent<Props, State> {

    private readonly churchFetcher = DefaultChurchFetcher;

    constructor(props: Props) {
        super(props);
        this.state = {
            church: {}
        }
    }

    render() {

        if (!this.state.church.data || !this.props.place) return null;

        const place = this.props.place;
        const church = this.state.church.data;

        return <>

            <h1>{place.name}</h1>

            {church.denomination} founded <DateRange date={church.established}/>

        </>;

    }

    componentDidMount() {
        this.loadChurch();
    }

    componentDidUpdate(prevProps: Readonly<Props>, prevState: Readonly<State>) {
        if (this.props.placeId != prevProps.placeId)
            this.loadChurch();
    }

    private loadChurch() {
        const id = this.props.placeId;
        if (!id) return;
        asyncLoadData(id, this.churchFetcher.fetchChurch, church => this.setState({church}));
    }

}