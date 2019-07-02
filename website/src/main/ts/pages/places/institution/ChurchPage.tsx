import * as React from "react";
import {DEFAULT_CHURCH_LOADER} from "../../../components/places/ChurchLoader";
import {PlacesPageProps} from "../PlacesBasePage";
import {AsyncData, asyncLoadData} from "../../../components/fetch/AsyncData";
import {InstitutionInfo} from "./InstitutionInfo";
import {Institution} from "../../../protobuf/generated/institution_pb";
import {PlaceMap} from "../../../components/places/PlaceMap";

type Props = PlacesPageProps

type State = {
    church: AsyncData<Institution.AsObject>
}

export default class ChurchPage extends React.PureComponent<Props, State> {

    private readonly churchLoader = DEFAULT_CHURCH_LOADER;

    constructor(props: Props) {
        super(props);
        this.state = {
            church: {}
        }
    }

    render() {

        const church = this.state.church.data;
        if (!church) return null; //TODO loading

        return <div className="institution leftRest">

            <PlaceMap/>
            <InstitutionInfo institution={church}/>

        </div>;

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
        asyncLoadData(id, this.churchLoader.loadChurch, church => this.setState({church}));
    }

}