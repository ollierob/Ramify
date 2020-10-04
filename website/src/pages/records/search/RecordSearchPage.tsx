import * as React from "react";
import {FormEvent} from "react";
import {Button, Card, Cascader, Form, Input, Select} from "antd";
import {AsyncData, asyncLoadData} from "../../../components/fetch/AsyncData";
import {PlaceId} from "../../../components/places/Place";
import {DEFAULT_PLACE_LOADER} from "../../../components/places/PlaceLoader";
import {CascaderOptionType} from "antd/es/cascader";
import {DateRange, yearsToDateRange} from "../../../components/date/DateRange";
import {RecordSet} from "../../../protobuf/generated/record_pb";
import RecordSetTable from "./RecordSetTable";
import {DEFAULT_RECORD_LOADER, RecordSetOptions} from "../../../components/records/RecordLoader";
import {readPageHash, updatePageHash} from "../../../components/Page";
import {hashToRecordSearch, recordSearchToHash} from "../../../components/search/RecordSearchHandler";
import {RecordBasePage, RecordBasePageProps} from "../RecordBasePage";
import {SearchIcon} from "../../../components/images/Icons";
import RegionCascader from "../../../components/places/RegionCascader";
import {parseIsoDate, toIsoDate} from "../../../components/date/Date";
import {RecordType, recordTypeName, recordTypes} from "../../../components/records/RecordType";

const {Option} = Select;

type Props = RecordBasePageProps;

type OptionsState = {
    selectedRangeName: string[];
    selectedRange?: DateRange;
    selectedRegion?: PlaceId;
    selectedTypes: RecordType[]
    recordName?: string;
}

type State = OptionsState & {
    recordSets: AsyncData<ReadonlyArray<RecordSet.AsObject>>;
}

export default class RecordSearchPage extends RecordBasePage<State> {

    private readonly placeLoader = DEFAULT_PLACE_LOADER;
    private readonly recordLoader = DEFAULT_RECORD_LOADER;
    private readonly setSelectedRegion = (id: PlaceId) => this.setState({selectedRegion: id});

    constructor(props: Props) {
        super(props);
        this.state = {
            ...optionsToState(hashToRecordSearch(readPageHash())),
            recordSets: {},
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
                            placeholder="Matching all names"
                            size="large"
                            autoComplete="off"
                            value={this.state.recordName}
                            onChange={e => this.setState({recordName: e.target.value})}/>
                    </Form.Item>

                    <Form.Item className="filter">
                        Filter by record place:
                        <br/>
                        <RegionCascader
                            maxDepth={3}
                            placeLoader={this.placeLoader}
                            placeHolder="Matching all places"
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
                            value={this.state.selectedRangeName}
                            onChange={(selectedRange, selectedOptions) => this.setState({selectedRangeName: selectedRange, selectedRange: selectedOptions[selectedRange.length - 1]?.range})}
                            options={YearRanges}
                            displayRender={this.renderRange}/>
                    </Form.Item>

                    <Form.Item className="filter">
                        Filter by record type:
                        <br/>
                        <Select
                            mode="multiple"
                            size="large"
                            value={this.state.selectedTypes}
                            onChange={values => this.setState({selectedTypes: values})}
                            maxTagCount={1}
                            placeholder="Matching all types">
                            {RecordTypeOptions}
                        </Select>
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
        this.setPageTitle("Record search");
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
            fromDate: toIsoDate(this.state.selectedRange?.earliest),
            toDate: toIsoDate(this.state.selectedRange?.latest),
            type: this.state.selectedTypes,
            onlyParents: true
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
            range: yearsToDateRange(century, centuryEnd - 1)
        };
        options.push(centuryOption);
        for (let decade = 0; decade < 100; decade += 10) {
            const decadeStart = century + decade;
            const decadeEnd = decadeStart + 10;
            const decadeOption: CascaderOptionType = {
                label: decadeStart + " - " + decadeEnd,
                value: decadeStart + "-" + decadeEnd,
                range: yearsToDateRange(decadeStart, decadeEnd - 1)
            };
            centuryOption.children.push(decadeOption);
        }
    }
    return options;
}

function optionsToState(options: RecordSetOptions): OptionsState {
    const dateRange = parseDateRange(options.fromDate, options.toDate);
    return {
        recordName: options.name,
        selectedRange: dateRange,
        selectedRangeName: parseRangeName(dateRange),
        selectedTypes: options.type || []
    };
}

function parseDateRange(from: string, to: string): DateRange {
    if (!from && !to) return null;
    return {approximate: false, earliest: parseIsoDate(from), latest: parseIsoDate(to)};
}

function parseRangeName(range: DateRange): string[] {
    if (!range || !range.earliest || !range.latest) return [];
    const fromYear = range.earliest.year;
    if (fromYear % 10 != 0) return [];
    const toYear = range.latest.year + 1;
    if (toYear % 10 != 0) return [];
    if (toYear % 100 == 0 && fromYear + 100 == toYear) return [fromYear + "-" + toYear];
    const fromCentury = fromYear - (fromYear % 100);
    return [fromCentury + "-" + (fromCentury + 100), fromYear + "-" + toYear];
}

const RecordTypeOptions: ReadonlyArray<React.ReactNode> = recordTypes(false)
    .sort()
    .map(type => <Option key={type} value={type}>{recordTypeName(type)}</Option>);