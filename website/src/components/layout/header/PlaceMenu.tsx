import * as React from "react";
import {List, Tabs} from "antd";
import {PlaceFavouritesHandler} from "../../places/PlaceFavourites";
import {NoData} from "../../style/NoData";
import {Place} from "../../../protobuf/generated/place_pb";
import {PlaceLink} from "../../places/PlaceLink";

type Props = PlaceFavouritesHandler;

type State = {
    activeTab?: string;
}

export class PlaceMenu extends React.PureComponent<Props, State> {

    private readonly setRecent = () => this.setState({activeTab: "recent"});
    private readonly setFavourites = () => this.setState({activeTab: "favourites"});
    private readonly setSearch = () => this.setState({activeTab: "search"});

    constructor(props) {
        super(props);
        this.state = {activeTab: "search"};
    }

    render() {

        return <div className="tabbedSubmenu">

            <Tabs tabPosition="left" activeKey={this.state.activeTab} size="large">

                <Tabs.TabPane key="search" tab={<TabTitle title="Search" onMouseover={this.setSearch}/>}>

                </Tabs.TabPane>

                <Tabs.TabPane key="favourites" tab={<TabTitle title="Favourites" onMouseover={this.setFavourites}/>}>
                    <FavouritesList {...this.props}/>
                </Tabs.TabPane>

                <Tabs.TabPane key="recent" tab={<TabTitle title="Recent" onMouseover={this.setRecent}/>}>
                    <RecentList/>
                </Tabs.TabPane>

            </Tabs>

        </div>;

    }

}

const TabTitle = (props: {title: React.ReactNode, onMouseover: () => void}) => {
    return <div onMouseOver={props.onMouseover}>{props.title}</div>;
};

const RecentList = (props: {}) => {
    return <NoData text="No recent places"/>;
};

const FavouritesList = (props: PlaceFavouritesHandler) => {
    const favourites: Place.AsObject[] = [...props.placeFavourites()];
    if (!favourites.length) return <NoData text="No favourite places"/>;
    return <List
        dataSource={favourites}
        renderItem={place => <PlaceListItem place={place}/>}/>;
};

const PlaceListItem = (props: {place: Place.AsObject}) => {
    return <List.Item>
        <PlaceLink place={props.place} showType/>
    </List.Item>;
};