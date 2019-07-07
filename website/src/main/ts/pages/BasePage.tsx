import * as React from "react";
import HeaderMenu from "./HeaderMenu";
import "./Core"
import {getPlaceHistory, PlaceHistory} from "./places/PlaceHistory";

export default abstract class BasePage<S = any> extends React.PureComponent<any, S> {

    render() {

        return <>

            <Header>
                {this.menu()}
            </Header>

            <Body>
                {this.body()}
            </Body>

        </>

    }

    menu(): React.ReactNode {
        return <HeaderMenu
            placeHistory={this.placeHistory()}
            active={this.active()}/>
    }

    placeHistory(): PlaceHistory {
        return getPlaceHistory();
    }

    abstract body(): React.ReactNode;

    abstract active(): string;

}

const Header = (props: {children: React.ReactNode}) => <div className="header">
    {props.children}
</div>

const Body = (props: {children: React.ReactNode}) => <div className="body">
    {props.children}
</div>