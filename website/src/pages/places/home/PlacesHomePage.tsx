import * as React from "react";
import {AsyncData, asyncLoadData} from "../../../components/fetch/AsyncData";
import {Place} from "../../../protobuf/generated/place_pb";
import {Card} from "antd";
import {DEFAULT_PLACE_LOADER} from "../../../components/places/PlaceLoader";
import {Loading} from "../../../components/style/Loading";
import {Flag} from "../../../components/images/Flag";
import {placeHref} from "../PlaceLinks";
import {PlaceBasePage, PlaceBasePageProps} from "../PlaceBasePage";

type Props = PlaceBasePageProps;

type State = {
    countries: AsyncData<ReadonlyArray<Place.AsObject>>
}

export default class PlacesHomePage extends PlaceBasePage<State> {

    private readonly placeLoader = DEFAULT_PLACE_LOADER;

    constructor(props: Props) {
        super(props);
        this.state = {
            countries: {loading: true}
        };
    }

    body() {

        return <div className="home">

            <h1>Places</h1>

            {this.state.countries.loading && <Loading/>}
            {this.state.countries.data && this.state.countries.data.map(country => <CountryCard country={country}/>)}

        </div>;

    }

    componentDidMount() {
        super.componentDidMount();
        asyncLoadData(null, this.placeLoader.loadCountries, countries => this.setState({countries}));
    }

}

const CountryCard = (props: {country: Place.AsObject}) => {
    const country = props.country;
    if (!country) return null;
    return <Card
        className="country"
        title={<><Flag iso={country.iso}/><a href={placeHref(country)}>{country.name}</a></>}>

    </Card>;
};

