import * as React from "react";
import HeaderMenu, {HeaderMenuType} from "../components/layout/header/HeaderMenu";
import "./Core";
import {PlaceHistoryHandler, SessionPlaceHistoryHandler} from "../components/places/PlaceHistory";
import {PlaceFavouritesHandler, RemotePlaceFavouritesHandler} from "../components/places/PlaceFavourites";
import {RouteComponentProps} from "react-router";

export type BasePageProps = RouteComponentProps<any>;

export default abstract class BasePage<S = any> extends React.PureComponent<BasePageProps, S> {

    readonly placeFavourites: PlaceFavouritesHandler = RemotePlaceFavouritesHandler;
    readonly placeHistory: PlaceHistoryHandler = SessionPlaceHistoryHandler;
    private readonly onHashChange = () => this.hashChanged();

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

    urlParameter(key: string): string {
        const location = this.props.location;
        if (!location) return null;
        return new URLSearchParams(location.search).get(key);
    }

    setPageTitle(title?: string) {
        document.title = "Ramify" + (title ? " | " + title : "");
    }

    componentDidMount() {
        this.setPageTitle();
        window.addEventListener("hashchange", this.onHashChange, false);
    }

    componentWillUnmount() {
        window.removeEventListener("hashchange", this.onHashChange, false);
    }

    protected hashChanged(): void {
    }

}

const Header = (props: {children: React.ReactNode}) => <div className="header">
    {props.children}
</div>;

const Body = (props: {children: React.ReactNode}) => <div className="body">
    {props.children}
</div>;