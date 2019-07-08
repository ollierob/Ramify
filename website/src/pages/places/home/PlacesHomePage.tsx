import * as React from "react";
import {PlacesPageProps} from "../PlacesPage";
import {AsyncData, asyncLoadData} from "../../../components/fetch/AsyncData";
import {Place} from "../../../protobuf/generated/place_pb";
import {Card} from "antd";
import {DEFAULT_PLACE_LOADER} from "../../../components/places/PlaceLoader";
import {Loading} from "../../../components/Loading";
import {Flag} from "../../../components/images/Flag";
import {placeHref} from "../../../components/places/Place";

type Props = PlacesPageProps;

type State = {
    countries: AsyncData<ReadonlyArray<Place.AsObject>>
}

export default class PlacesHomePage extends React.PureComponent<Props, State> {

    private readonly placeLoader = DEFAULT_PLACE_LOADER;

    constructor(props) {
        super(props);
        this.state = {
            countries: {loading: true}
        }
    }


    render() {

        return <div className="home">

            <h1>Places</h1>

            {this.state.countries.loading && <Loading/>}
            {this.state.countries.data && this.state.countries.data.map(country => <CountryCard country={country}/>)}

        </div>

    }

    componentDidMount() {
        asyncLoadData(null, this.placeLoader.loadCountries, countries => this.setState({countries}))
    }

}

const CountryCard = (props: {country: Place.AsObject}) => {
    const country = props.country;
    if (!country) return null;
    return <Card
        className="country"
        title={<><Flag iso={country.iso}/> <a href={placeHref(country)}>{country.name}</a></>}>

    </Card>
};

