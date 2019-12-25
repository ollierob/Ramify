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
import {readPageHash, updatePageHash} from "../../../components/Page";
import {hashToRecordSearch, recordSearchToHash} from "../../../components/search/RecordSearchHandler";
import {RecordBasePage, RecordBasePageProps} from "../RecordBasePage";
import {SearchIcon} from "../../../components/images/Icons";
import RegionCascader from "../../../components/places/RegionCascader";

type Props = RecordBasePageProps;

type State = {
    selectedRange: string[];
    recordSets: AsyncData<ReadonlyArray<RecordSet.AsObject>>;
    recordName?: string;
    selectedRegion?: PlaceId;
}

export default class RecordSearchPage extends RecordBasePage<State> {

    private readonly placeLoader = DEFAULT_PLACE_LOADER;
    private readonly recordLoader = DEFAULT_RECORD_LOADER;
    private readonly setSelectedRegion = (id: PlaceId) => this.setState({selectedRegion: id});

    constructor(props: Props) {
        super(props);
        const options = hashToRecordSearch(readPageHash());
        this.state = {
            selectedRange: [],
            recordSets: {},
            recordName: options.name
        };
        this.renderRange = this.renderRange.bind(this);
        this.doSearch = this.doSearch.bind(this);
    }

    body() {

        return <div className="content">

            <Card
                className="records large"
                title={<>Record Sets</>}>

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
                        <RegionCascader
                            maxDepth={3}
                            placeLoader={this.placeLoader}
                            size="large"
                            onSelect={this.setSelectedRegion}/>
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
                            options={YearRanges}
                            displayRender={this.renderRange}/>
                    </Form.Item>

                    <Form.Item className="filter">
                        <br/>
                        <Button
                            htmlType="submit"
                            size="large"
                            type="primary"
                            disabled={this.state.recordSets.loading}>
                            <SearchIcon/> Search for records
                        </Button>
                    </Form.Item>

                </Form>

                <div className="table">
                    <RecordSetTable
                        recordSets={this.state.recordSets}/>
                </div>

            </Card>

        </div>;

    }

    componentDidMount() {
        super.componentDidMount();
        if (this.state.recordName || this.state.selectedRegion)
            this.doSearch();
    }

    private renderRange(range: string[]): React.ReactNode {
        if (!range.length) return null;
        return range[range.length - 1];
    }

    private doSearch(e?: FormEvent<HTMLFormElement>) {
        if (e) e.preventDefault();
        const options: RecordSetOptions = {
            place: this.state.selectedRegion,
            name: this.state.recordName,
        };
        asyncLoadData(options, this.recordLoader.loadRecordSets, recordSets => this.setState({recordSets}));
        updatePageHash(recordSearchToHash(options));
    }

}


const YearRanges = generateYearRanges();

function generateYearRanges(): CascaderOptionType[] {
    const options: CascaderOptionType[] = [];
    for (let century = 1300; century <= 1900; century += 100) {
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