import * as React from "react";
import {PlacesPageProps} from "../PlacesPage";
import {Loading} from "../../../components/style/Loading";
import {PlaceMap} from "../../../components/places/PlaceMap";
import {PlaceTypeDescription} from "../../../components/places/PlaceTypeDescription";
import {PlaceInfo} from "../area/PlaceInfo";

type Props = PlacesPageProps;

export default class BuildingPage extends React.PureComponent<Props> {

    render() {

        if (this.props.loading) return <Loading/>;

        const bundle = this.props.place;
        if (!bundle) return null;

        return <div className="building leftRest">

            <PlaceMap
                area={true}
                loading={this.props.loading}
                place={bundle.place}
                position={bundle.position}/>

            <PlaceTypeDescription
                description={bundle.typedescription}/>

            <PlaceInfo
                {...this.props}
                place={bundle.place}
                description={bundle.description}/>

        </div>;

    }

}