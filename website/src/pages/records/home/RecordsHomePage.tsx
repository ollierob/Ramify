import * as React from "react";
import {FormEvent} from "react";
import {Button, Card, Cascader, Form, Input} from "antd";
import {AsyncData, asyncLoadData} from "../../../components/fetch/AsyncData";
import {Place} from "../../../protobuf/generated/place_pb";
import {Flag} from "../../../components/images/Flag";
import {PlaceId} from "../../../components/places/Place";
import {DEFAULT_PLACE_LOADER} from "../../../components/places/PlaceLoader";
import {CascaderOptionType} from "antd/es/cascader";
import {YearRange} from "../../../components/date/DateRange";
import {RecordSet} from "../../../protobuf/generated/record_pb";
import RecordSetTable from "./RecordSetTable";
import {DEFAULT_RECORD_LOADER, RecordSetOptions} from "../../../components/records/RecordLoader";
import {QueryMap, readPageHash, updatePageHash} from "../../../components/Page";

type Props = {}

type State = {
    selectedRegion: PlaceId[];
    regions: CascaderOptionType[];
    selectedRange: string[];
    ranges: CascaderOptionType[];
    recordSets: AsyncData<ReadonlyArray<RecordSet.AsObject>>;
    recordName?: string;
}

export default class RecordsHomePage extends React.PureComponent<Props, State> {

    private readonly placeLoader = DEFAULT_PLACE_LOADER;
    private readonly recordLoader = DEFAULT_RECORD_LOADER;

    constructor(props) {
        super(props);
        const hash = readPageHash();
        this.state = {
            selectedRegion: [],
            regions: [],
            selectedRange: [],
            ranges: generateYearRanges(),
            recordSets: {},
            recordName: hash["searchName"]
        };
        this.renderRange = this.renderRange.bind(this);
        this.loadLeafPlace = this.loadLeafPlace.bind(this);
        this.doSearch = this.doSearch.bind(this);
    }


    render() {

        return <Card
            className="records large"
            title={<>Records</>}>

            <Form layout="inline" onSubmit={this.doSearch}>

                <Form.Item className="search">
                    Search by record name:
                    <br/>
                    <Input
                        placeholder="Matching all record names"
                        size="large"
                        value={this.state.recordName}
                        onChange={e => this.setState({recordName: e.target.value})}/>
                </Form.Item>

                <Form.Item className="filter">
                    Filter by record place:
                    <br/>
                    <Cascader
                        placeholder="Matching all places"
                        size="large"
                        changeOnSelect
                        value={this.state.selectedRegion}
                        onChange={selectedRegion => this.setState({selectedRegion})}
                        options={this.state.regions}
                        loadData={this.loadLeafPlace}
                        displayRender={this.renderPlace}/>
                </Form.Item>

                <Form.Item className="filter">
                    Filter by record date:
                    <br/>
                    <Cascader
                        placeholder="Matching all dates"
                        size="large"
                        changeOnSelect
                        value={this.state.selectedRange}
                        onChange={selectedRange => this.setState({selectedRange})}
                        options={this.state.ranges}
                        displayRender={this.renderRange}/>
                </Form.Item>

                <Form.Item className="filter">
                    <br/>
                    <Button
                        htmlType="submit"
                        size="large"
                        disabled={this.state.recordSets.loading}>
                        Load records
                    </Button>
                </Form.Item>

            </Form>

            <div className="table">
                <RecordSetTable
                    recordSets={this.state.recordSets}/>
            </div>

        </Card>;

    }

    componentDidMount() {
        this.placeLoader.loadCountries()
            .then(countries => this.setState({regions: generateCountryOptions(countries)}));
    }

    private renderPlace(places: PlaceId[]): React.ReactNode {
        if (!places.length) return null;
        return places[places.length - 1];
    }

    private renderRange(range: string[]): React.ReactNode {
        if (!range.length) return null;
        return range[range.length - 1];
    }

    private loadLeafPlace(selected: CascaderOptionType[]) {
        const target = selected[selected.length - 1];
        if (target.depth == 1) {
            target.loading = true;
            this.placeLoader.loadChildren(target.value, 1)
                .then(children => {
                    target.loading = false;
                    target.children = children.map<CascaderOptionType>(child => ({
                        label: <><Flag iso={child.iso}/>{child.name}</>,
                        value: child.id,
                        depth: 2
                    }));
                }).then(() => this.setState(current => ({regions: [...current.regions]})));
        }
    }

    private doSearch(e: FormEvent<HTMLFormElement>) {
        e.preventDefault();
        const options: RecordSetOptions = {
            place: this.state.selectedRegion.length ? this.state.selectedRegion[this.state.selectedRegion.length - 1] : null,
            name: this.state.recordName,
        };
        asyncLoadData(options, this.recordLoader.loadRecordSets, recordSets => this.setState({recordSets}));
        updatePageHash(searchHash(options));
    }

}

function generateCountryOptions(countries: ReadonlyArray<Place.AsObject>): CascaderOptionType[] {
    if (!countries) return [];
    return countries.map<CascaderOptionType>(country => ({
        label: <><Flag iso={country.iso}/>{country.name}</>,
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

function searchHash(options: RecordSetOptions): QueryMap {
    return {
        base: "search",
        searchName: options.name,
        searchPlace: options.place,
    };
}