import * as React from "react";
import HeaderMenu, {HeaderMenuType} from "../components/layout/header/HeaderMenu";
import "./Core";
import {getSessionPlaceHistory} from "../components/places/PlaceHistory";
import {PlaceList} from "../components/places/Place";
import {PlaceFavouritesHandler, RemotePlaceFavouritesHandler} from "../components/places/PlaceFavourites";
import {RouteComponentProps} from "react-router";

export type BasePageProps = RouteComponentProps<any>;

export default abstract class BasePage<S = any> extends React.PureComponent<BasePageProps, S> {

    readonly placeFavourites: PlaceFavouritesHandler = RemotePlaceFavouritesHandler;

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
            placeHistory={this.placeHistory()}
            active={this.active()}/>;
    }

    placeHistory(): PlaceList {
        //TODO override without causing mount/update loops
        return getSessionPlaceHistory();
    }

    abstract body(): React.ReactNode;

    abstract active(): HeaderMenuType;

    urlParameter(key: string): string {
        const location = this.props.location;
        if (!location) return null;
        return new URLSearchParams(location.search).get(key);
    }

    setPageTitle(title: string) {
        document.title = title;
    }

}

const Header = (props: {children: React.ReactNode}) => <div className="header">
    {props.children}
</div>;

const Body = (props: {children: React.ReactNode}) => <div className="body">
    {props.children}
</div>;