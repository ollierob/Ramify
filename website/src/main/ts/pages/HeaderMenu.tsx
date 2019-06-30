import * as React from "react";
import {Menu} from "antd";
import {PeopleIcon, PlacesIcon, TreeIcon} from "../components/Icons";

type Props = {
    active: string;
}

export default class HeaderMenu extends React.PureComponent<Props> {

    render() {
        return <Menu mode="horizontal" selectedKeys={[this.props.active]} className="menu">
            <Menu.Item key="trees"><TreeIcon/> Trees</Menu.Item>
            <Menu.Item key="people"><PeopleIcon/> People</Menu.Item>
            <Menu.Item key="places"><PlacesIcon/> Places</Menu.Item>
        </Menu>;
    }

}