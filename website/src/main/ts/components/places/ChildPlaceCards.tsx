import * as React from "react";
import {Place} from "../../protobuf/generated/place_pb";
import {stringMultimap} from "../Maps";
import {Card} from "antd";
import {placeHref} from "./Place";
import {PlaceType, placeTypeKey, placeTypeName, sortByPlaceName, sortByPlaceType} from "./PlaceType";

type Props = {
    loading: boolean;
    childPlaces: ReadonlyArray<Place.AsObject>
}

type State = {
    groupedPlaces: PlaceTypeMap
}

export default class ChildPlaceCards extends React.PureComponent<Props, State> {

    constructor(props) {
        super(props);
        this.state = {
            groupedPlaces: {}
        }
    }

    render() {

        if (this.props.loading) return null; //TODO

        return <div className="childPlaces">
            {Object.keys(this.state.groupedPlaces)
                .sort(sortByPlaceType)
                .map(type => <TypeCard type={type as PlaceType} places={this.state.groupedPlaces[type]}/>)}
        </div>;

    }

    componentDidMount() {
        this.group(this.props.childPlaces);
    }

    componentDidUpdate(prevProps: Readonly<Props>) {
        if (this.props.childPlaces != prevProps.childPlaces)
            this.group(this.props.childPlaces)
    }

    private group(places: ReadonlyArray<Place.AsObject>) {
        if (!places) return;
        const grouped = stringMultimap(places, place => placeTypeKey(place.type), sortByPlaceName);
        this.setState({groupedPlaces: grouped})
    }

}

type PlaceTypeMap = {[type: string]: ReadonlyArray<Place.AsObject>}

const TypeCard = (props: {type: PlaceType, places: ReadonlyArray<Place.AsObject>}) => {
    let places = props.places;
    if (!places || !places.length) return null;
    return <Card
        className="childPlace"
        title={placeTypeName(props.type, true)}>
        <ul>
            {places.map(place => <li>
                <a href={placeHref(place)}>{place.name}</a>
            </li>)}
        </ul>
    </Card>
};