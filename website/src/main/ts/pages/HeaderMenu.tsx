import * as React from "react";
import {Menu} from "antd";
import {PeopleIcon, PlacesIcon, RecordsIcon, TreeIcon} from "../components/Icons";
import {getPlaceHistory} from "./places/PlaceHistory";
import {placeHref} from "../components/places/Place";
import {Place} from "../protobuf/generated/place_pb";

type Props = {
    active: string;
}

export default class HeaderMenu extends React.PureComponent<Props> {

    render() {

        const history = getPlaceHistory();

        return <Menu mode="horizontal" selectedKeys={[this.props.active]} className="menu">
            <Menu.Item key="trees">
                <TreeIcon/> Trees
            </Menu.Item>
            <Menu.Item key="people">
                <PeopleIcon/> People
            </Menu.Item>
            <Menu.SubMenu key="places" title={<a href="/places"><PlacesIcon/> Places</a>}>
                <Menu.ItemGroup title="Recent places">
                    {!history.length && <Menu.Item key="" disabled>No recent places.</Menu.Item>}
                    {history.map(h => <Menu.Item key={h.id}><PlaceMenuItem place={h}/></Menu.Item>)}
                </Menu.ItemGroup>
            </Menu.SubMenu>
            <Menu.Item key="records">
                <a href="/records"><RecordsIcon/> Records</a>
            </Menu.Item>
        </Menu>;

    }

}

const PlaceMenuItem = (props: {place: Place.AsObject}) => {
    const place = props.place;
    return <>
        <a href={placeHref(place)}>{place.name}</a>
        {" "}
        ({place.type})
    </>
}