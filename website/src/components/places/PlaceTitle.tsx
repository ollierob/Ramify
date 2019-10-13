import * as React from "react";
import {Place, PlaceDescription} from "../../protobuf/generated/place_pb";
import {placeTypeName} from "./PlaceType";
import {LinkTags} from "../style/Links";
import {isPlaceFavourite, PlaceFavouritesHandler} from "./PlaceFavourites";
import {FavouritesIcon} from "../images/Icons";
import {Flag} from "../images/Flag";
import {PlaceList} from "./Place";
import {Tag} from "antd";
import {PlaceLink} from "./PlaceLink";

type Props = PlaceFavouritesHandler & {
    place: Place.AsObject,
    description: PlaceDescription.AsObject;
}

type State = {
    favourites: PlaceList;
    isFavourite?: boolean
}

export class PlaceTitle extends React.PureComponent<Props, State> {

    private readonly setFavourites = (favourites: PlaceList) => this.setState({favourites});

    constructor(props: Props) {
        super(props);
        this.state = {
            favourites: []
        };
    }

    render() {

        const place = this.props.place;
        if (!place) return null;

        const isFavourite = this.state.isFavourite;

        return <>

            <Flag iso={place.iso}/>

            <b>{place.name}</b>

            {" "}

            <span className="unimportant">
                {placeTypeName(place.type)}
            </span>

            {" "}

            <FavouritesIcon
                className={isFavourite && "favourite"}
                onClick={() => {
                    if (isFavourite) this.props.removePlaceFavourite(place).then(this.setFavourites);
                    else this.props.addPlaceFavourite(place).then(this.setFavourites);
                }}/>

            {" "}

            <TypeTags {...this.props}/>

            <LinkTags
                links={this.props.description && this.props.description.linkList}/>

        </>;

    }

    componentDidMount() {
        this.loadPlaceFavourites();
    }

    private loadPlaceFavourites() {
        this.props.placeFavourites().then(this.setFavourites);
    }

    componentDidUpdate(prevProps: Readonly<Props>, prevState: Readonly<State>) {
        if (this.props.place != prevProps.place || this.state.favourites != prevState.favourites)
            this.setState({isFavourite: isPlaceFavourite(this.props.place.id, this.state.favourites)});
    }

}

const TypeTags = (props: {description: PlaceDescription.AsObject}) => {
    return <div className="tags">
        {props.description.laterbecameList && props.description.laterbecameList.map(place => <DefunctPlaceTag place={place}/>)}
        {props.description.coterminousList && props.description.coterminousList.map(place => <CoterminousPlaceTag place={place}/>)}
    </div>;
};

const DefunctPlaceTag = (props: {place: Place.AsObject}) => {
    return <Tag color="orange">
        Replaced by <PlaceLink place={props.place} showType/>
    </Tag>;
};

const CoterminousPlaceTag = (props: {place: Place.AsObject}) => {
    return <Tag color="geekblue">
        Coterminous with <PlaceLink place={props.place} showType/>
    </Tag>;
};