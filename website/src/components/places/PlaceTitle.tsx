import * as React from "react";
import {Place, PlaceDescription} from "../../protobuf/generated/place_pb";
import {placeTypeName} from "./PlaceType";
import {LinkTags} from "../style/Links";
import {isPlaceFavourite, PlaceFavouritesHandler} from "./PlaceFavourites";
import {FavouritesIcon} from "../images/Icons";
import {Flag} from "../images/Flag";

type State = {place: Place.AsObject, isFavourite?: boolean}

export const PlaceTitle = (props: {place: Place.AsObject, description: PlaceDescription.AsObject} & PlaceFavouritesHandler) => {

    const [state, setState] = React.useState<State>(() => ({
        place: props.place,
        isFavourite: props.place && isPlaceFavourite(props.place.id, props.placeFavourites())
    }));

    const place = props.place;
    if (!place) return null;

    if (place != state.place) setState({
        place: place,
        isFavourite: isPlaceFavourite(props.place.id, props.placeFavourites())
    });

    const isFavourite = state.isFavourite;

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
                if (isFavourite) props.removePlaceFavourite(place);
                else props.addPlaceFavourite(place);
                setState({place: place, isFavourite: !isFavourite});
            }}/>

        <LinkTags
            links={props.description && props.description.linkList}/>

    </>;

};