import * as React from "react";
import {RouteComponentProps} from "react-router";
import {PlaceId} from "../../../components/places/Place";
import {DefaultChurchFetcher} from "../../../components/places/ChurchesFetcher";
import {AsyncData, asyncLoadData} from "../../../components/fetch/AsyncData";

type Props = RouteComponentProps<any>;

type State = {
    region?: PlaceId
    churches?: AsyncData<ReadonlyArray<PlaceId>>
}

export default class ChurchesPage extends React.PureComponent<Props, State> {

    private readonly churchFetcher = DefaultChurchFetcher;

    constructor(props: Props) {
        super(props);
        this.state = {}
    }

    render() {

        return <>

            <h1>Churches</h1>

        </>;

    }

    componentDidMount() {
        this.readLocation();
    }

    componentDidUpdate(prevProps: Readonly<Props>, prevState: Readonly<State>) {

        if (this.props.location != prevProps.location)
            this.readLocation();

        if (this.state.region != prevState.region)
            this.loadRegion(this.state.region);

    }

    private readLocation() {
        const location = this.props.location;
        if (!location) return;
        const search = new URLSearchParams(location.search);
        this.setState({
            region: search.get("region")
        })
    }

    private loadRegion(region: PlaceId) {
        asyncLoadData(
            region,
            r => this.churchFetcher.fetchChurches(r),
            churches => this.setState({churches}))
    }


}