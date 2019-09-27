import * as React from "react";
import {Dropdown, Icon, Menu} from "antd";
import {FavouritesIcon, PeopleIcon, PlacesIcon, RecordsIcon, SearchIcon, TreeIcon} from "../components/images/Icons";
import {PlaceList} from "../components/places/Place";
import {Place} from "../protobuf/generated/place_pb";
import {placeTypeName} from "../components/places/PlaceType";
import {placeHref} from "./places/PlaceLinks";
import {PlaceFavouritesHandler} from "../components/places/PlaceFavourites";

type Props = {
    active: HeaderMenuType;
    placeHistory: PlaceList;
    placeFavourites: PlaceFavouritesHandler;
}

export default class HeaderMenu extends React.PureComponent<Props> {

    render() {

        return <Menu mode="horizontal" className="menu">

            <Menu.SubMenu title={<><TreeIcon/> People</>} className={this.props.active == "people" && ActiveClass}>

            </Menu.SubMenu>

            <Menu.SubMenu title={<><PlacesIcon/> Places</>} className={this.props.active == "places" && ActiveClass}>

            </Menu.SubMenu>

            <Menu.SubMenu title={<><RecordsIcon/> Records</>} className={this.props.active == "records" && ActiveClass}>

            </Menu.SubMenu>

        </Menu>;

    }

}

const ActiveClass = "ant-menu-item-selected";
export type HeaderMenuType = "people" | "places" | "records"