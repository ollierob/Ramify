import * as React from "react";
import {PlacesPageProps} from "../PlacesBasePage";
import {PlaceMap} from "../../../components/places/PlaceMap";
import {TownInfo} from "./TownInfo";

type Props = PlacesPageProps;

export default class TownPage extends React.PureComponent<Props> {

    render() {

        return <div className="town">

            <PlaceMap/>
            <TownInfo/>

        </div>;

    }

}