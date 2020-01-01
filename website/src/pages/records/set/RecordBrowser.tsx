import * as React from "react";
import {Tabs} from "antd";
import {RecordSet} from "../../../protobuf/generated/record_pb";
import {RecordPaginationHandler} from "../../../components/records/RecordPaginationHandler";
import RecordImageGallery from "./RecordImageGallery";
import FamilyRecordTable from "./family/FamilyRecordTable";
import IndividualRecordTable from "./individual/IndividualRecordTable";
import {readPageHash, updatePageHash} from "../../../components/Page";
import {ImageIcon, PeopleIcon, PersonIcon, StatisticsIcon} from "../../../components/images/Icons";

type Props = RecordPaginationHandler & {
    recordSet: RecordSet.AsObject;
    hasChildren?: boolean;
}

type State = {
    activeTab?: ActiveTab;
}

type ActiveTab = "families" | "individuals" | "images" | "statistics"

export class RecordBrowser extends React.PureComponent<Props, State> {

    constructor(props: Props) {
        super(props);
        this.state = {
            activeTab: this.activeTab(readPageHash().view) || "families"
        };
    }

    private activeTab(active: string): ActiveTab {
        switch (active) {
            case "families":
            case "individuals":
            case "images":
            case "statistics":
                return active;
            default:
                return null;
        }
    }

    render() {

        return <Tabs
            className="records bordered"
            size="large"
            activeKey={this.state.activeTab}
            onChange={activeTab => this.setState({activeTab: activeTab as ActiveTab})}>

            <Tabs.TabPane
                key="families"
                tab={<><PeopleIcon/> Families</>}>
                <FamilyRecordTable {...this.props} showRecordSet={this.props.hasChildren}/>
            </Tabs.TabPane>

            <Tabs.TabPane
                key="individuals"
                tab={<><PersonIcon/> Individuals</>}>
                <IndividualRecordTable {...this.props} showRecordSet={this.props.hasChildren}/>
            </Tabs.TabPane>

            <Tabs.TabPane
                key="images"
                tab={<><ImageIcon/> Image browser</>}>
                <RecordImageGallery {...this.props}/>
            </Tabs.TabPane>

            <Tabs.TabPane
                key="statistics"
                tab={<><StatisticsIcon/> Statistics</>}
                disabled/>

        </Tabs>;

    }

    componentDidUpdate(prevProps: Readonly<Props>, prevState: Readonly<State>) {
        if (this.state.activeTab != prevState.activeTab)
            updatePageHash({view: this.state.activeTab});
    }

}