import * as React from "react";
import "./Core";

import HeaderMenu, {HeaderMenuType} from "../components/layout/header/HeaderMenu";
import {PlaceFavouritesHandler, RemotePlaceFavouritesHandler} from "../components/places/PlaceFavourites";
import {PlaceHistoryHandler, SessionPlaceHistoryHandler} from "../components/places/PlaceHistory";

export default abstract class BasePage<P = any, S = any> extends React.PureComponent<P, S> {

    readonly placeFavourites: PlaceFavouritesHandler = RemotePlaceFavouritesHandler;
    readonly placeHistory: PlaceHistoryHandler = SessionPlaceHistoryHandler;

    render() {

        return <>

            <Header>
                {this.menu()}
            </Header>

            <Body>
                {this.body()}
            </Body>

        </>;

    }

    menu(): React.ReactNode {
        return <HeaderMenu
            {...this.placeFavourites}
            {...this.placeHistory}
            active={this.active()}/>;
    }

    abstract body(): React.ReactNode;

    abstract active(): HeaderMenuType;

    setPageTitle(title?: string) {
        document.title = "Ramify" + (title ? " | " + title : "");
    }

}

const Header = (props: {children: React.ReactNode}) => <div className="header">
    {props.children}
</div>;

const Body = (props: {children: React.ReactNode}) => <div className="body">
    {props.children}
</div>;