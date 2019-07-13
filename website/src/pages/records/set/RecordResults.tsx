import * as React from "react";
import {RecordSearchHandler} from "../../../components/search/RecordSearchHandler";
import {Tabs} from "antd";
import {AsyncData} from "../../../components/fetch/AsyncData";
import {Record, RecordSet} from "../../../protobuf/generated/record_pb";
import {RecordTable} from "./RecordTable";
import {RecordPaginationHandler} from "../../../components/records/RecordPaginationHandler";
import {RecordType, recordTypeFromValue} from "../../../components/records/RecordType";

type Props = RecordPaginationHandler & RecordSearchHandler & {
    recordSet: RecordSet.AsObject;
    records: AsyncData<ReadonlyArray<Record.AsObject>>;
    searchResults: AsyncData<ReadonlyArray<Record.AsObject>>;
}

type State = {
    activeTab?: string;
    type?: RecordType;
}

export class RecordResults extends React.PureComponent<Props, State> {

    constructor(props) {
        super(props);
        this.state = {
            activeTab: "records",
        };
    }

    render() {

        return <Tabs
            className="bordered"
            size="large"
            activeKey={this.state.activeTab}
            onChange={activeTab => this.setState({activeTab})}>

            <Tabs.TabPane
                key="records"
                tab={"Records"}>
                <RecordTable
                    {...this.props}
                    type={this.state.type}
                    loading={this.props.records.loading}
                    records={this.props.records.data}/>
            </Tabs.TabPane>

            <Tabs.TabPane
                key="images"
                tab="Image browser"
                disabled>

            </Tabs.TabPane>

            <Tabs.TabPane
                key="search"
                tab={"Search results"}
                disabled={!this.props.searchResults.query}>
                <RecordTable
                    type={this.state.type}
                    loading={this.props.searchResults.loading}
                    records={this.props.searchResults.data}/>
            </Tabs.TabPane>

        </Tabs>;

    }

    componentDidUpdate(prevProps: Readonly<Props>) {

        if (this.props.searchResults.loading && !prevProps.searchResults.loading && this.state.activeTab != "search")
            this.setState({activeTab: "search"});

        if (this.props.recordSet != prevProps.recordSet)
            this.setState({type: recordTypeFromValue(this.props.recordSet.type)});

    }

}