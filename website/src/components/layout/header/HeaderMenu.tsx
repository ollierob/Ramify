import * as React from "react";
import {Menu} from "antd";
import {PlacesIcon, RecordsIcon, TreeIcon} from "../../images/Icons";
import {PlaceList} from "../../places/Place";
import {PlaceFavouritesHandler} from "../../places/PlaceFavourites";
import "./HeaderMenu.css";
import {PlaceMenu} from "./place/PlaceMenu";
import {RecordMenu} from "./RecordMenu";
import {PeopleMenu} from "./PeopleMenu";

type Props = PlaceFavouritesHandler & {
    active: HeaderMenuType;
    placeHistory: PlaceList;
}

type State = {
    openMenus?: string[];
}

export default class HeaderMenu extends React.PureComponent<Props, State> {

    private readonly setOpenMenus = (openMenus: string[]) => this.setState({openMenus});

    constructor(props) {
        super(props);
        this.state = {openMenus: []};
    }

    render() {

        return <Menu
            mode="horizontal"
            className="menu"
            onOpenChange={this.setOpenMenus}
            openKeys={this.state.openMenus}>

            <Menu.SubMenu
                key="people"
                title={<a href="/people"><TreeIcon/> People</a>}
                className={this.props.active == "people" && ActiveClass}>
                <PeopleMenu {...this.props} open={this.state.openMenus.includes("people")}/>
            </Menu.SubMenu>

            <Menu.SubMenu
                key="places"
                title={<><PlacesIcon/> Places</>}
                className={this.props.active == "places" && ActiveClass}>
                <PlaceMenu {...this.props} open={this.state.openMenus.includes("places")}/>
            </Menu.SubMenu>

            <Menu.SubMenu
                key="records"
                title={<a href="/records"><RecordsIcon/> Records</a>}
                className={this.props.active == "records" && ActiveClass}>
                <RecordMenu {...this.props} open={this.state.openMenus.includes("records")}/>
            </Menu.SubMenu>

        </Menu>;

    }

}

const ActiveClass = "ant-menu-item-selected";
export type HeaderMenuType = "people" | "places" | "records"