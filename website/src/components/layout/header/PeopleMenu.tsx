import * as React from "react";
import {Tabs} from "antd";
import {NoData} from "../../style/NoData";

type Props = {}

type State = {
    activeTab?: string;
}

export class PeopleMenu extends React.PureComponent<Props, State> {

    private readonly setTrees = () => this.setState({activeTab: "trees"});

    constructor(props) {
        super(props);
        this.state = {activeTab: "trees"};
    }

    render() {

        return <div className="tabbedSubmenu">

            <Tabs tabPosition="left" activeKey={this.state.activeTab} size="large">

                <Tabs.TabPane key="trees" tab={<TabTitle title="Trees" onMouseover={this.setTrees}/>}>
                    <Trees/>
                </Tabs.TabPane>

            </Tabs>

        </div>;

    }

}

const TabTitle = (props: {title: React.ReactNode, onMouseover: () => void}) => {
    return <div onMouseOver={props.onMouseover}>{props.title}</div>;
};

const Trees = (props: {}) => {
    return <NoData text="No trees"/>;
};