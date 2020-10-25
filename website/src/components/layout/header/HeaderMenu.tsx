import * as React from "react";
import {Menu} from "antd";
import {CommunityIcon, PlacesIcon, RecordsIcon, TreeIcon} from "../../images/Icons";
import {PlaceFavouritesHandler} from "../../places/PlaceFavourites";
import "./HeaderMenu.css";
import {PlaceMenu} from "./places/PlaceMenu";
import {RecordMenu} from "./records/RecordMenu";
import {PeopleMenu} from "./people/PeopleMenu";
import {PlaceHistoryHandler} from "../../places/PlaceHistory";
import CommunityMenu from "./community/CommunityMenu";
import UserMenu from "./UserMenu";

type Props = PlaceFavouritesHandler & PlaceHistoryHandler & {
    active: HeaderMenuType;
}

type State = {
    openMenu?: string;
}

export default class HeaderMenu extends React.PureComponent<Props, State> {

    private readonly setOpenMenus = (openMenus: string[]) => {
        this.setState({openMenu: openMenus.length == 0 ? null : openMenus[openMenus.length - 1]});
    };

    constructor(props) {
        super(props);
        this.state = {};
    }

    render() {

        return <>

            <Menu
                mode="horizontal"
                className="menu"
                subMenuCloseDelay={0.3}
                onOpenChange={this.setOpenMenus}
                multiple={false}
                openKeys={this.state.openMenu && [this.state.openMenu]}>

                <Menu.SubMenu
                    key="people"
                    title={<a href="/people"><TreeIcon/> People</a>}
                    className={this.props.active == "people" && ActiveClass}>
                    <PeopleMenu {...this.props} open={this.state.openMenu == "people"}/>
                </Menu.SubMenu>

                <Menu.SubMenu
                    key="places"
                    title={<><PlacesIcon/> Places</>}
                    className={this.props.active == "places" && ActiveClass}>
                    <PlaceMenu {...this.props} open={this.state.openMenu == "places"}/>
                </Menu.SubMenu>

                <Menu.SubMenu
                    key="records"
                    title={<a href="/records"><RecordsIcon/> Records</a>}
                    className={this.props.active == "records" && ActiveClass}>
                    <RecordMenu {...this.props} open={this.state.openMenu == "records"}/>
                </Menu.SubMenu>

                <Menu.SubMenu
                    key="community"
                    title={<a href="/community"><CommunityIcon/> Community</a>}
                    className={this.props.active == "community" && ActiveClass}>
                    <CommunityMenu {...this.props}/>
                </Menu.SubMenu>

            </Menu>

            <UserMenu/>

        </>;

    }

}

const ActiveClass = "ant-menu-item-selected";
export type HeaderMenuType = "people" | "places" | "records" | "community"