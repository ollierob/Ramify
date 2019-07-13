import * as React from "react";
import {RecordSearchHandler} from "../../../components/search/RecordSearchHandler";
import {Tabs} from "antd";
import {AsyncData} from "../../../components/fetch/AsyncData";
import {Record} from "../../../protobuf/generated/record_pb";

type Props = RecordSearchHandler & {
    searchResults: AsyncData<ReadonlyArray<Record.AsObject>>;
}

type State = {
    activeTab?: string;
}

export class RecordResults extends React.PureComponent<Props, State> {

    constructor(props) {
        super(props);
        this.state = {
            activeTab: "records"
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
                tab="Records">
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

            </Tabs.TabPane>

        </Tabs>;

    }

    componentDidUpdate(prevProps: Readonly<Props>) {

        if (this.props.searchResults.loading && !prevProps.searchResults.loading && this.state.activeTab != "search")
            this.setState({activeTab: "search"});

    }

}