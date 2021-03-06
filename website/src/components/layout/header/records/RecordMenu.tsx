import * as React from "react";
import {Tabs} from "antd";
import {NoData} from "../../../style/NoData";
import {RecordSearch} from "./RecordSearch";

type Props = {
    open: boolean;
}

type State = {
    activeTab?: string;
}

export class RecordMenu extends React.PureComponent<Props, State> {

    private readonly setRecent = () => this.setState({activeTab: "recent"});
    private readonly setFavourites = () => this.setState({activeTab: "favourites"});
    private readonly setSearch = () => this.setState({activeTab: "search"});

    constructor(props) {
        super(props);
        this.state = {activeTab: "search"};
    }

    render() {

        return <div className="tabbedSubmenu">

            <Tabs tabPosition="left" activeKey={this.state.activeTab} size="large">

                <Tabs.TabPane key="search" tab={<TabTitle title="Search" onMouseover={this.setSearch}/>}>
                    <RecordSearch parentOpen={this.props.open}/>
                </Tabs.TabPane>

                <Tabs.TabPane key="favourites" tab={<TabTitle title="Favourites" onMouseover={this.setFavourites}/>}>
                    <FavouritesList/>
                </Tabs.TabPane>

                <Tabs.TabPane key="recent" tab={<TabTitle title="Recent" onMouseover={this.setRecent}/>}>
                    <RecentList/>
                </Tabs.TabPane>

            </Tabs>

        </div>;

    }

}

const TabTitle = (props: {title: React.ReactNode, onMouseover: () => void}) => {
    return <div onMouseOver={props.onMouseover}>{props.title}</div>;
};

const RecentList = (props: {}) => {
    return <NoData text="No recent records"/>;
};

const FavouritesList = (props: {}) => {
    return <NoData text="No favourite records"/>;
};