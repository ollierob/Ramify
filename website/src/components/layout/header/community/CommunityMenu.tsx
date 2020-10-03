import * as  React from "react";
import {Tabs} from "antd";

type Props = {}

type State = {
    activeTab?: string
}

export default class CommunityMenu extends React.PureComponent<Props, State> {

    constructor(props) {
        super(props);
        this.state = {};
    }

    render() {

        return <div className="tabbedSubmenu">

            <Tabs tabPosition="left" activeKey={this.state.activeTab} size="large">

            </Tabs>

        </div>;

    }

}