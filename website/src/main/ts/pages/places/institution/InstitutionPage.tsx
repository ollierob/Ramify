import * as React from "react";
import {PlacesPageProps} from "../PlacesBasePage";
import {AsyncData} from "../../../components/fetch/AsyncData";
import {InstitutionInfo} from "./InstitutionInfo";
import {Institution} from "../../../protobuf/generated/institution_pb";
import {PlaceMap} from "../../../components/places/PlaceMap";
import {Loading} from "../../../components/Loading";
import {PlaceId} from "../../../components/places/Place";

type Props = PlacesPageProps

type State = {
    institution: AsyncData<Institution.AsObject>;
}

export abstract class InstitutionPage extends React.PureComponent<Props, State> {

    constructor(props: Props) {
        super(props);
        this.state = {
            institution: {}
        }
    }

    render() {

        if (this.props.loading) return <Loading/>;

        const bundle = this.props.place;
        if (!bundle) return null;

        return <div className="institution leftRest">

            <PlaceMap
                place={bundle && bundle.place}
                loading={this.props.loading}
                position={bundle && bundle.position}
                defaultZoom={14}/>/>

            <InstitutionInfo
                {...this.props}
                place={bundle.place}
                description={bundle.description}
                institution={this.state.institution.data}/>

        </div>;

    }

    componentDidMount() {
        if (this.props.placeId)
            this.loadInstitution(this.props.placeId);
    }

    componentDidUpdate(prevProps: Readonly<Props>, prevState: Readonly<State>) {
        if (this.props.placeId != prevProps.placeId)
            this.loadInstitution(this.props.placeId);
    }

    abstract loadInstitution(place: PlaceId);

}