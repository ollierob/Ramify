import * as React from "react";
import HeaderMenu from "./HeaderMenu";
import "./Core";
import {getPlaceHistory} from "../components/places/PlaceHistory";
import {PlaceList} from "../components/places/Place";
import {getPlaceFavourites} from "../components/places/PlaceFavourites";

export default abstract class BasePage<P = any, S = any> extends React.PureComponent<P, S> {

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
            placeHistory={this.placeHistory()}
            placeFavourites={this.placeFavourites()}
            active={this.active()}/>;
    }

    placeHistory(): PlaceList {
        //TODO override without causing mount/update loops
        return getPlaceHistory();
    }

    placeFavourites(): PlaceList {
        return getPlaceFavourites();
    }

    abstract body(): React.ReactNode;

    abstract active(): string;

}

const Header = (props: {children: React.ReactNode}) => <div className="header">
    {props.children}
</div>;

const Body = (props: {children: React.ReactNode}) => <div className="body">
    {props.children}
</div>;