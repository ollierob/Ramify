import * as React from "react";
import {Menu} from "antd";

type Props = {
    active: string;
}

export default class HeaderMenu extends React.PureComponent<Props> {

    render() {
        return <Menu mode="horizontal" selectedKeys={[this.props.active]} className="menu">
            <Menu.Item key="trees">Trees</Menu.Item>
            <Menu.Item key="people">People</Menu.Item>
            <Menu.Item key="places">Places</Menu.Item>
        </Menu>;
    }

}