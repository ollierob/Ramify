import * as React from "react";
import {List, Tabs} from "antd";
import {PlaceFavouritesHandler} from "../../places/PlaceFavourites";
import {NoData} from "../../style/NoData";
import {Place} from "../../../protobuf/generated/place_pb";
import {placeHref} from "../../../pages/places/PlaceLinks";
import {PlaceLink} from "../../places/PlaceLink";

type Props = PlaceFavouritesHandler;

type State = {
    activeTab?: string;
}

export class PlaceSubmenu extends React.PureComponent<Props, State> {

    private readonly setRecent = () => this.setState({activeTab: "recent"});
    private readonly setFavourites = () => this.setState({activeTab: "favourites"});
    private readonly setSearch = () => this.setState({activeTab: "search"});

    constructor(props) {
        super(props);
        this.state = {activeTab: "recent"};
    }

    render() {

        return <div className="placeMenu">

            <Tabs tabPosition="left" activeKey={this.state.activeTab}>

                <Tabs.TabPane key="recent" tab={<TabTitle title="Recent" onMouseover={this.setRecent}/>}>

                </Tabs.TabPane>

                <Tabs.TabPane key="favourites" tab={<TabTitle title="Favourites" onMouseover={this.setFavourites}/>}>

                    <FavouritesList {...this.props}/>

                </Tabs.TabPane>

                <Tabs.TabPane key="search" tab={<TabTitle title="Search" onMouseover={this.setSearch}/>}>

                </Tabs.TabPane>

            </Tabs>

        </div>;

    }

}

const TabTitle = (props: {title: React.ReactNode, onMouseover: () => void}) => {
    return <div onMouseOver={props.onMouseover}>{props.title}</div>;
};

const FavouritesList = (props: PlaceFavouritesHandler) => {
    const favourites: Place.AsObject[] = [...props.placeFavourites()];
    if (!favourites.length) return <NoData/>;
    return <List
        dataSource={favourites}
        renderItem={place => <PlaceListItem place={place}/>}/>;
};

const PlaceListItem = (props: {place: Place.AsObject}) => {
    return <List.Item>
        <PlaceLink place={props.place} showType/>
    </List.Item>;
};