import * as React from "react";
import {Tabs} from "antd";
import {RecordSet} from "../../../protobuf/generated/record_pb";
import {RecordPaginationHandler} from "../../../components/records/RecordPaginationHandler";
import RecordImageGallery from "./RecordImageGallery";
import FamilyRecordTable from "./family/FamilyRecordTable";
import IndividualRecordTable from "./individual/IndividualRecordTable";

type Props = RecordPaginationHandler & {
    recordSet: RecordSet.AsObject;
    showRecordSet?: boolean;
}

type State = {
    activeTab?: string;
}

export class RecordBrowser extends React.PureComponent<Props, State> {

    constructor(props: Props) {
        super(props);
        this.state = {
            activeTab: "families",
        };
    }

    render() {

        return <Tabs
            className="records bordered"
            size="large"
            activeKey={this.state.activeTab}
            onChange={activeTab => this.setState({activeTab})}>

            <Tabs.TabPane
                key="families"
                tab="Family records">
                <FamilyRecordTable {...this.props}/>
            </Tabs.TabPane>

            <Tabs.TabPane
                key="individuals"
                tab="Individual records">
                <IndividualRecordTable {...this.props}/>
            </Tabs.TabPane>

            <Tabs.TabPane
                key="images"
                tab="Image browser">
                <RecordImageGallery {...this.props}/>
            </Tabs.TabPane>

        </Tabs>;

    }

}