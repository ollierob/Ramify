import * as React from "react";
import {Button, Form, Input, List} from "antd";
import {DEFAULT_RECORD_LOADER, RecordSetOptions} from "../../../records/RecordLoader";
import {RecordSet} from "../../../../protobuf/generated/record_pb";
import {AsyncData, asyncLoadData} from "../../../fetch/AsyncData";
import {NoData} from "../../../style/NoData";
import {recordSetHref} from "../../../../pages/records/RecordLinks";
import {SearchIcon} from "../../../images/Icons";
import {ChangeEvent, FormEvent} from "react";

type Props = {
    parentOpen: boolean;
}

type State = {
    searchString?: string;
    searchResults: AsyncData<ReadonlyArray<RecordSet.AsObject>>
}

export class RecordSearch extends React.PureComponent<Props, State> {

    private readonly setSearchString = (e: ChangeEvent<HTMLInputElement>) => this.setState({searchString: e.target.value});
    private readonly recordLoader = DEFAULT_RECORD_LOADER;

    constructor(props: Props) {
        super(props);
        this.state = {searchResults: {}};
        this.doSearch = this.doSearch.bind(this);
    }

    render() {

        return <div className="record menuSearch">

            <Form onSubmit={this.doSearch}>

                <Form.Item className="search">
                    <Input
                        size="large"
                        placeholder="Place name"
                        value={this.state.searchString}
                        onChange={this.setSearchString}/>
                </Form.Item>

                <Form.Item>
                    <Button
                        htmlType="submit"
                        type="primary"
                        disabled={!this.state.searchString || this.state.searchResults.loading}>
                        <SearchIcon/> Search
                    </Button>
                </Form.Item>

            </Form>

            <div className="results">
                <SearchResults {...this.state}/>
            </div>

        </div>;

    }

    private doSearch(e?: FormEvent<HTMLFormElement>) {
        if (e) e.preventDefault();
        const name = this.state.searchString;
        if (!name) return;
        const options: RecordSetOptions = {name, limit: 15};
        asyncLoadData(options, o => this.recordLoader.loadRecordSets(o), searchResults => this.setState({searchResults}));
    }

}

const SearchResults = (props: {searchResults: AsyncData<ReadonlyArray<RecordSet.AsObject>>}) => {

    const results = props.searchResults;
    if (results.loading || results.data) {
        const data = results.data ? [...results.data] : [];
        return <List
            loading={results.loading}
            dataSource={data}
            renderItem={renderSearchResult}/>;
    }

    return <NoData/>;

};

function renderSearchResult(recordSet: RecordSet.AsObject) {
    return <List.Item key={recordSet.id}>
        <a href={recordSetHref(recordSet)}>
            {recordSet.longtitle}
        </a>
    </List.Item>;
}