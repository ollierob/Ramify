import * as React from "react";
import {RouteComponentProps} from "react-router";

export default class PlacesHomePage extends React.PureComponent<RouteComponentProps<any>> {

    render() {

        return <>

            <h1>Places</h1>

            Path: {this.props.match.path}
            <br/>
            URL: {this.props.match.url}

        </>

    }

}