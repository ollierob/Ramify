import * as React from "react";
import {Menu} from "antd";
import {PlacesIcon, RecordsIcon, TreeIcon} from "../../images/Icons";
import {PlaceList} from "../../places/Place";
import {PlaceFavouritesHandler} from "../../places/PlaceFavourites";
import "./HeaderMenu.css";
import {PlaceMenu} from "./PlaceMenu";
import {RecordMenu} from "./RecordMenu";
import {PeopleMenu} from "./PeopleMenu";

type Props = PlaceFavouritesHandler & {
    active: HeaderMenuType;
    placeHistory: PlaceList;
}

export default class HeaderMenu extends React.PureComponent<Props> {

    render() {

        return <Menu mode="horizontal" className="menu">

            <Menu.SubMenu
                title={<a href="/people"><TreeIcon/> People</a>}
                className={this.props.active == "people" && ActiveClass}>
                <PeopleMenu {...this.props}/>
            </Menu.SubMenu>

            <Menu.SubMenu
                title={<a href="/places"><PlacesIcon/> Places</a>}
                className={this.props.active == "places" && ActiveClass}>
                <PlaceMenu {...this.props}/>
            </Menu.SubMenu>

            <Menu.SubMenu
                title={<a href="/records"><RecordsIcon/> Records</a>}
                className={this.props.active == "records" && ActiveClass}>
                <RecordMenu {...this.props}/>
            </Menu.SubMenu>

        </Menu>;

    }

}

const ActiveClass = "ant-menu-item-selected";
export type HeaderMenuType = "people" | "places" | "records"