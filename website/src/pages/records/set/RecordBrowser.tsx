import * as React from "react";
import {Tabs} from "antd";
import {AsyncData} from "../../../components/fetch/AsyncData";
import {RecordSet} from "../../../protobuf/generated/record_pb";
import {IndividualRecordTable} from "./IndividualRecordTable";
import {RecordPaginationHandler} from "../../../components/records/RecordPaginationHandler";
import RecordImageGallery from "./RecordImageGallery";
import {EnrichedIndividualRecord} from "../../../components/records/RecordLoader";

type Props = RecordPaginationHandler & {
    recordSet: RecordSet.AsObject;
    records: AsyncData<ReadonlyArray<EnrichedIndividualRecord>>;
    searchResults: AsyncData<ReadonlyArray<EnrichedIndividualRecord>>;
    showRecordSet?: boolean;
}

type State = {
    activeTab?: string;
}

export class RecordBrowser extends React.PureComponent<Props, State> {

    constructor(props: Props) {
        super(props);
        this.state = {
            activeTab: "individuals",
        };
    }

    render() {

        return <Tabs
            className="records bordered"
            size="large"
            activeKey={this.state.activeTab}
            onChange={activeTab => this.setState({activeTab})}>

            <Tabs.TabPane
                key="records"
                tab="Records"
                disabled>

            </Tabs.TabPane>

            <Tabs.TabPane
                key="individuals"
                tab="Individuals">
                <IndividualRecordTable
                    {...this.props}
                    loading={this.props.records.loading}
                    records={this.props.records.data}/>
            </Tabs.TabPane>

            <Tabs.TabPane
                key="images"
                tab="Image browser">
                <RecordImageGallery
                    {...this.props}/>
            </Tabs.TabPane>

            <Tabs.TabPane
                key="search"
                tab={"Search results"}
                disabled={!this.props.searchResults.query}>
                <IndividualRecordTable
                    {...this.props}
                    loading={this.props.searchResults.loading}
                    records={this.props.searchResults.data}/>
            </Tabs.TabPane>

        </Tabs>;

    }

    componentDidUpdate(prevProps: Readonly<Props>) {

        if (this.props.searchResults.loading && !prevProps.searchResults.loading && this.state.activeTab != "search")
            this.setState({activeTab: "search"});

    }

}