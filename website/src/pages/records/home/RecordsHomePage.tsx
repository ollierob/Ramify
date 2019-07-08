import * as React from "react";
import {Card, Select} from "antd";
import {AsyncData, asyncLoadData} from "../../../components/fetch/AsyncData";
import {Place} from "../../../protobuf/generated/place_pb";
import {Flag} from "../../../components/images/Flag";
import {PlaceId} from "../../../components/places/Place";
import {DEFAULT_PLACE_LOADER} from "../../../components/places/PlaceLoader";

type Props = {}

type State = {
    selectedCountries: ReadonlyArray<PlaceId>
    countries: AsyncData<ReadonlyArray<Place.AsObject>>;
}

export default class RecordsHomePage extends React.PureComponent<Props, State> {

    private readonly placeLoader = DEFAULT_PLACE_LOADER;

    constructor(props) {
        super(props);
        this.state = {
            selectedCountries: [],
            countries: {}
        }
    }


    render() {

        return <Card
            className="records large"
            title={<>Records</>}>

            <div className="filter">
                Filter by country:
                <br/>
                <Select
                    placeholder="Showing all countries"
                    mode="multiple"
                    size="large"
                    value={this.state.selectedCountries}
                    onChange={values => this.setState({selectedCountries: values})}
                    loading={this.state.countries.loading}>
                    {this.state.countries.data && this.state.countries.data.map(countrySelectOption)}
                </Select>
            </div>

            <div className="filter">
                Filter by date:
                <br/>
                <Select
                    placeholder="Showing all dates"
                    mode="multiple"
                    size="large">

                </Select>
            </div>

        </Card>;

    }

    componentDidMount() {
        this.loadCountries();
    }

    private loadCountries() {
        asyncLoadData(null, this.placeLoader.loadCountries, countries => this.setState({countries}))
    }

}

function countrySelectOption(country: Place.AsObject) {
    return <Select.Option key={country.id} value={country.id}>
        <Flag iso={country.iso}/> {country.name}
    </Select.Option>
}