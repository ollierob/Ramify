import * as React from "react";
import {Tabs} from "antd";
import {NoData} from "../../style/NoData";
import {createTreeHref} from "../../../pages/people/PeopleLinks";

type Props = {}

type State = {
    activeTab?: string;
}

export class PeopleMenu extends React.PureComponent<Props, State> {

    private readonly setTrees = () => this.setState({activeTab: "trees"});
    private readonly setRecent = () => this.setState({activeTab: "recent"});
    private readonly setSearch = () => this.setState({activeTab: "search"});

    constructor(props) {
        super(props);
        this.state = {activeTab: "search"};
    }

    render() {

        return <div className="tabbedSubmenu">

            <Tabs tabPosition="left" activeKey={this.state.activeTab} size="large">

                <Tabs.TabPane key="search" tab={<TabTitle title="Search" onMouseover={this.setSearch}/>}>
                    <Search/>
                </Tabs.TabPane>

                <Tabs.TabPane key="trees" tab={<TabTitle title="Trees" onMouseover={this.setTrees}/>}>
                    <Trees/>
                </Tabs.TabPane>

                <Tabs.TabPane key="recent" tab={<TabTitle title="Recent" onMouseover={this.setRecent}/>}>
                    <Recent/>
                </Tabs.TabPane>

            </Tabs>

        </div>;

    }

}

const TabTitle = (props: {title: React.ReactNode, onMouseover: () => void}) => {
    return <div onMouseOver={props.onMouseover}>{props.title}</div>;
};

const Search = () => {
    return <></>;
};

const Trees = (props: {}) => {
    return <><a href={createTreeHref()}>Create new tree</a></>;
};

const Recent = (props: {}) => {
    return <NoData text="No recent people"/>;
};