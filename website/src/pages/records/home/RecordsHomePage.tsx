import * as React from "react";
import {Card, Cascader, Select} from "antd";
import {AsyncData, asyncLoadData} from "../../../components/fetch/AsyncData";
import {Place} from "../../../protobuf/generated/place_pb";
import {Flag} from "../../../components/images/Flag";
import {PlaceId} from "../../../components/places/Place";
import {DEFAULT_PLACE_LOADER} from "../../../components/places/PlaceLoader";
import {CascaderOptionType} from "antd/es/cascader";
import {YearRange} from "../../../components/date/DateRange";

type Props = {}

type State = {
    selectedRegion: PlaceId[];
    countries: AsyncData<ReadonlyArray<Place.AsObject>>;
    regions: CascaderOptionType[];
    selectedRange: string[];
    ranges: CascaderOptionType[];
}

export default class RecordsHomePage extends React.PureComponent<Props, State> {

    private readonly placeLoader = DEFAULT_PLACE_LOADER;

    constructor(props) {
        super(props);
        this.state = {
            selectedRegion: [],
            countries: {},
            regions: [],
            selectedRange: [],
            ranges: generateYearRanges()
        };
        this.renderRange = this.renderRange.bind(this);
        this.loadLeafPlace = this.loadLeafPlace.bind(this);
    }


    render() {

        return <Card
            className="records large"
            title={<>Records</>}>

            <div className="filter">
                Filter by region:
                <br/>
                <Cascader
                    placeholder="Showing all countries"
                    size="large"
                    changeOnSelect
                    value={this.state.selectedRegion}
                    onChange={selectedRegion => this.setState({selectedRegion})}
                    options={this.state.regions}
                    loadData={this.loadLeafPlace}/>
            </div>

            <div className="filter">
                Filter by date:
                <br/>
                <Cascader
                    placeholder="Showing all dates"
                    size="large"
                    changeOnSelect
                    value={this.state.selectedRange}
                    onChange={selectedRange => this.setState({selectedRange})}
                    options={this.state.ranges}
                    displayRender={this.renderRange}/>
            </div>

        </Card>;

    }

    componentDidMount() {
        this.loadCountries();
    }

    componentDidUpdate(prevProps: Readonly<Props>, prevState: Readonly<State>) {

        if (this.state.countries != prevState.countries)
            this.setState({regions: generateCountryOptions(this.state.countries.data)})

    }

    private renderRange(range: string[]) {
        if (!range.length) return null;
        return range[range.length - 1];
    }

    private loadCountries() {
        asyncLoadData(null, this.placeLoader.loadCountries, countries => this.setState({countries}))
    }

    private loadLeafPlace(selected: CascaderOptionType[]) {
        const target = selected[selected.length - 1];
        if (target.depth == 1) {
            target.loading = true;
            this.placeLoader.loadChildren(target.value, 1)
                .then(children => {
                    target.loading = false;
                    target.children = children.map<CascaderOptionType>(c => ({label: c.name, value: c.id, depth: 2}))
                }).then(() => this.setState(current => ({regions: [...current.regions]})));
        }
    }

}

function generateCountryOptions(countries: ReadonlyArray<Place.AsObject>): CascaderOptionType[] {
    if (!countries) return [];
    return countries.map<CascaderOptionType>(country => ({
        label: <><Flag iso={country.iso}/> {country.name}</>,
        value: country.id,
        isLeaf: false,
        depth: 1
    }));
}

function generateYearRanges(): CascaderOptionType[] {
    const options: CascaderOptionType[] = [];
    for (let century = 1400; century <= 1900; century += 100) {
        const centuryEnd = century + 100;
        const centuryOption: CascaderOptionType = {
            label: century + "s",
            value: century + "-" + centuryEnd,
            children: [],
            range: yearRange(century, centuryEnd)
        };
        options.push(centuryOption);
        for (let decade = 0; decade < 100; decade += 10) {
            const decadeStart = century + decade;
            const decadeEnd = decadeStart + 10;
            const decadeOption: CascaderOptionType = {
                label: decadeStart + " - " + decadeEnd,
                value: decadeStart + "-" + decadeEnd,
                range: yearRange(decadeStart, decadeEnd)
            };
            centuryOption.children.push(decadeOption);
        }
    }
    return options;
}

function yearRange(from: number, to: number): YearRange {
    return {from, to};
}