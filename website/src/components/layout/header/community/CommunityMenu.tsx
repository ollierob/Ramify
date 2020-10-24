import * as  React from "react";
import {Tabs} from "antd";

type Props = {}

type State = {
    activeTab?: string
}

export default class CommunityMenu extends React.PureComponent<Props, State> {

    private readonly setGroupsActive = () => this.setState({activeTab: "groups"});
    private readonly setForumsActive = () => this.setState({activeTab: "forums"});

    constructor(props) {
        super(props);
        this.state = {};
    }

    render() {

        return <div className="tabbedSubmenu">

            <Tabs tabPosition="left" activeKey={this.state.activeTab} size="large">

                <Tabs.TabPane key="groups" tab={<TabTitle title="Groups" onMouseover={this.setGroupsActive}/>}/>

                <Tabs.TabPane key="forums" tab={<TabTitle title="Forums" onMouseover={this.setForumsActive}/>}/>

            </Tabs>

        </div>;

    }

}

const TabTitle = (props: {title: React.ReactNode, onMouseover: () => void}) => {
    return <div onMouseOver={props.onMouseover}>{props.title}</div>;
};