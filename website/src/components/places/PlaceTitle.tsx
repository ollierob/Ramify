import * as React from "react";
import {Place, PlaceDescription} from "../../protobuf/generated/place_pb";
import {placeTypeName} from "./PlaceType";
import {LinkTags} from "../style/Links";
import {isPlaceFavourite, PlaceFavouritesHandler} from "./PlaceFavourites";
import {FavouritesIcon} from "../images/Icons";
import {Flag} from "../images/Flag";
import {PlaceList} from "./Place";

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

};