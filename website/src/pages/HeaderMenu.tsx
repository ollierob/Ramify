import * as React from "react";
import {Icon, Menu} from "antd";
import {FavouritesIcon, PeopleIcon, PlacesIcon, RecordsIcon, SearchIcon, TreeIcon} from "../components/images/Icons";
import {PlaceList} from "../components/places/Place";
import {Place} from "../protobuf/generated/place_pb";
import {placeTypeName} from "../components/places/PlaceType";
import {placeHref} from "./places/PlaceLinks";

type Props = {
    active: string;
    placeHistory: PlaceList;
    placeFavourites: PlaceList;
}

export default class HeaderMenu extends React.PureComponent<Props> {

    render() {

        const history = this.props.placeHistory;
        const favourites = this.props.placeFavourites;

        return <Menu mode="horizontal" selectedKeys={[this.props.active]} className="menu">

            <Menu.SubMenu title={<><TreeIcon/> People</>}>
                <Menu.ItemGroup title={<>Trees</>}>
                    <Menu.Item disabled>No trees yet.</Menu.Item>
                </Menu.ItemGroup>
            </Menu.SubMenu>

            <Menu.SubMenu title={<><PlacesIcon/> Places</>}>
                <Menu.Item key="places">
                    <a href="/places"><SearchIcon/> Directory</a>
                </Menu.Item>
                <Menu.SubMenu title={<><FavouritesIcon/> Favourites</>}>
                    {!favourites.length && <Menu.Item disabled>No favourite places.</Menu.Item>}
                    {favourites.map(h => <Menu.Item key={h.id}><PlaceMenuItem place={h}/></Menu.Item>)}
                </Menu.SubMenu>
                <Menu.SubMenu title={<><Icon type="history"/> Recent</>}>
                    {!history.length && <Menu.Item key="" disabled>No recent places.</Menu.Item>}
                    {history.map(h => <Menu.Item key={h.id}><PlaceMenuItem place={h}/></Menu.Item>)}
                </Menu.SubMenu>
            </Menu.SubMenu>

            <Menu.SubMenu title={<><RecordsIcon/> Records</>}>
                <Menu.Item key="searchRecords">
                    <a href="/records"><SearchIcon/> Search</a>
                </Menu.Item>
            </Menu.SubMenu>

        </Menu>;

    }

}

const PlaceMenuItem = (props: {place: Place.AsObject}) => {
    const place = props.place;
    return <a href={placeHref(place)}>
        {place.name} ({placeTypeName(place.type)})
    </a>;
};