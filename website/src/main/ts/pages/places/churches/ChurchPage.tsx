import * as React from "react";
import {DefaultChurchFetcher} from "../../../components/places/ChurchesFetcher";
import {PlacesPageProps} from "../PlacesBasePage";
import {AsyncData, asyncLoadData} from "../../../components/fetch/AsyncData";
import {Church} from "../../../protobuf/generated/church_pb";
import {ChurchInfo} from "./ChurchInfo";
import {ChurchRecords} from "./ChurchRecords";

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

        const church = this.state.church.data;
        if (!church) return null; //TODO loading

        return <>

            <ChurchInfo church={church}/>
            <ChurchRecords church={church}/>

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