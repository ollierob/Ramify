import * as React from "react";
import {Icon, Menu} from "antd";
import {FavouritesIcon, PeopleIcon, PlacesIcon, RecordsIcon, TreeIcon} from "../components/Icons";
import {placeHref, PlaceList} from "../components/places/Place";
import {Place} from "../protobuf/generated/place_pb";
import {placeTypeName} from "../components/places/PlaceType";

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
            <Menu.Item key="trees">
                <TreeIcon/> Trees
            </Menu.Item>
            <Menu.Item key="people">
                <PeopleIcon/> People
            </Menu.Item>
            <Menu.SubMenu key="places" title={<a href="/places"><PlacesIcon/> Places</a>}>
                <Menu.SubMenu title={<><FavouritesIcon/> Favourites</>}>
                    {!favourites.length && <Menu.Item disabled>No favourite places.</Menu.Item>}
                    {favourites.map(h => <Menu.Item key={h.id}><PlaceMenuItem place={h}/></Menu.Item>)}
                </Menu.SubMenu>
                <Menu.SubMenu title={<><Icon type="history"/> Recent</>}>
                    {!history.length && <Menu.Item key="" disabled>No recent places.</Menu.Item>}
                    {history.map(h => <Menu.Item key={h.id}><PlaceMenuItem place={h}/></Menu.Item>)}
                </Menu.SubMenu>
            </Menu.SubMenu>
            <Menu.Item key="records">
                <a href="/records"><RecordsIcon/> Records</a>
            </Menu.Item>
        </Menu>;

    }

}

const PlaceMenuItem = (props: {place: Place.AsObject}) => {
    const place = props.place;
    return <a href={placeHref(place)}>
        {place.name} ({placeTypeName(place.type)})
    </a>
};