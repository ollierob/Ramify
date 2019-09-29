import * as React from "react";
import {Menu, Tabs} from "antd";
import {PlacesIcon, RecordsIcon, TreeIcon} from "../../images/Icons";
import {PlaceList} from "../../places/Place";
import {PlaceFavouritesHandler} from "../../places/PlaceFavourites";
import "./HeaderMenu.css";
import {PlaceSubmenu} from "./PlaceSubmenu";

type Props = PlaceFavouritesHandler & {
    active: HeaderMenuType;
    placeHistory: PlaceList;
}

export default class HeaderMenu extends React.PureComponent<Props> {

    render() {

        return <Menu mode="horizontal" className="menu">

            <Menu.SubMenu title={<><TreeIcon/> People</>} className={this.props.active == "people" && ActiveClass}>

            </Menu.SubMenu>

            <Menu.SubMenu title={<><PlacesIcon/> Places</>} className={this.props.active == "places" && ActiveClass}>
                <PlaceSubmenu {...this.props}/>
            </Menu.SubMenu>

            <Menu.SubMenu title={<><RecordsIcon/> Records</>} className={this.props.active == "records" && ActiveClass}>

            </Menu.SubMenu>

        </Menu>;

    }

}

const ActiveClass = "ant-menu-item-selected";
export type HeaderMenuType = "people" | "places" | "records"