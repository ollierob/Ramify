import * as React from "react";
import {Place} from "../../protobuf/generated/place_pb";
import {stringMultimap} from "../Maps";
import {Card} from "antd";
import {Img} from "../style/Img";
import {Loading} from "../style/Loading";
import {placeHref} from "../../pages/places/PlaceLinks";
import {sortPlacesByName} from "./Place";

type Props = {
    loading: boolean;
    childPlaces: ReadonlyArray<Place.AsObject>
    alsoSeePlaces: ReadonlyArray<Place.AsObject>
    noPlacesMessage?: React.ReactNode;
}

type State = {
    groupedPlaces: PlaceTypeMap
}

export default class ChildPlaceCards extends React.PureComponent<Props, State> {

    constructor(props) {
        super(props);
        this.state = {
            groupedPlaces: {}
        };
    }

    render() {

        if (this.props.loading) return <Loading/>; //TODO

        const keys = Object.keys(this.state.groupedPlaces);

        return <div className="childPlaces">

            {keys.sort().map(type => <TypeCard
                key={type}
                type={type}
                places={this.state.groupedPlaces[type]}/>)}

            <AlsoSee
                asCard={keys.length > 0}
                places={this.props.alsoSeePlaces}/>

            {!keys.length && !this.props.alsoSeePlaces && <>{this.props.noPlacesMessage}</>}

        </div>;

    }

    componentDidMount() {
        this.group(this.props.childPlaces);
    }

    componentDidUpdate(prevProps: Readonly<Props>) {
        if (this.props.childPlaces != prevProps.childPlaces)
            this.group(this.props.childPlaces);
    }

    private group(places: ReadonlyArray<Place.AsObject>) {
        if (!places) return;
        const grouped = stringMultimap(places, place => place.type.name, sortPlacesByName);
        this.setState({groupedPlaces: grouped});
    }

}

type PlaceTypeMap = {[type: string]: ReadonlyArray<Place.AsObject>}

const TypeCard = (props: {type: string, places: ReadonlyArray<Place.AsObject>}) => {
    let places = props.places;
    if (!places || !places.length) return null;
    return <Card
        className="placeCard"
        title={<><Img src={"/images/" + props.type.toLocaleLowerCase() + ".svg"}/> {props.type}</>}>
        <ul>
            {places.map(place => <li key={place.id}><PlaceLink place={place}/></li>)}
        </ul>
    </Card>;
};

const PlaceLink = (props: {place: Readonly<Place.AsObject>, showType?: boolean}) => {
    if (!props.place) return null;
    const link = <>
        <a href={placeHref(props.place)}>{props.place.name}</a>
        {props.showType && <> {props.place.type.name}</>}
    </>;
    return props.place.defunct ? <i>{link}</i> : link;
};

const AlsoSee = (props: {places: ReadonlyArray<Place.AsObject>, asCard: boolean}) => {

    const places = props.places;
    if (!places || !places.length) return null;
    const links = <ul>
        {places.map(place => <li><PlaceLink place={place} showType/></li>)}
    </ul>;

    if (props.asCard) return <Card
        className="placeCard"
        title={<>Also see</>}>
        {links}
    </Card>;

    return links;

};