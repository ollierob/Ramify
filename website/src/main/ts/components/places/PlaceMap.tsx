import * as React from "react";
import {HasClass} from "../style/HasClass";

type Props = HasClass & {};

export class PlaceMap extends React.PureComponent<Props> {

    render() {

        return <div className={this.props.className + " map"} style={this.props.style}>

        </div>;

    }

}