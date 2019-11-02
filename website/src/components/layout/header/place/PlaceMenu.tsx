import * as React from "react";
import {Tabs} from "antd";
import {PlaceFavouritesHandler} from "../../../places/PlaceFavourites";
import {NoData} from "../../../style/NoData";
import {PlaceList} from "../../../places/Place";
import {AsyncData, asyncLoadData} from "../../../fetch/AsyncData";
import {PlaceFavouritesList} from "./PlaceFavouritesList";
import {PlaceSearch} from "./PlaceSearch";

type Props = PlaceFavouritesHandler;

type State = {
    activeTab?: "recent" | "favourites" | "search";
    favourites: AsyncData<PlaceList>
}

export class PlaceMenu extends React.PureComponent<Props, State> {

    private readonly setRecent = () => this.setState({activeTab: "recent"});
    private readonly setFavourites = () => this.setState({activeTab: "favourites"});
    private readonly setSearch = () => this.setState({activeTab: "search"});

    constructor(props) {
        super(props);
        this.state = {
            activeTab: "search",
            favourites: {loading: true}
        };
    }

    render() {

        return <div className="tabbedSubmenu">

            <Tabs tabPosition="left" activeKey={this.state.activeTab} size="large">

                <Tabs.TabPane key="search" tab={<TabTitle title="Search" onMouseover={this.setSearch}/>}>
                    <PlaceSearch/>
                </Tabs.TabPane>

                <Tabs.TabPane key="favourites" tab={<TabTitle title="Favourites" onMouseover={this.setFavourites}/>}>
                    <PlaceFavouritesList
                        favourites={this.state.favourites.data}
                        loading={this.state.favourites.loading}/>
                </Tabs.TabPane>

                <Tabs.TabPane key="recent" tab={<TabTitle title="Recent" onMouseover={this.setRecent}/>}>
                    <RecentList/>
                </Tabs.TabPane>

            </Tabs>

        </div>;

    }

    componentDidMount() {
        this.loadFavourites();
    }

    componentDidUpdate(prevProps: Readonly<Props>, prevState: Readonly<State>) {
        if (this.state.activeTab == "favourites" && this.state.activeTab != prevState.activeTab)
            this.loadFavourites();
    }

    private loadFavourites() {
        asyncLoadData(null, this.props.placeFavourites, favourites => this.setState({favourites}));
    }

}

const TabTitle = (props: {title: React.ReactNode, onMouseover: () => void}) => {
    return <div onMouseOver={props.onMouseover}>{props.title}</div>;
};

const RecentList = (props: {}) => {
    return <NoData text="No recent places"/>;
};