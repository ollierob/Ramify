import {Place} from "../../protobuf/generated/place_pb";
import {PlaceId, PlaceList} from "./Place";
import {DEFAULT_PLACE_FAVOURITES_LOADER} from "./PlaceFavouritesLoader";

export function isPlaceFavourite(id: PlaceId, favourites: PlaceList): boolean {
    return id && favourites && favourites.some(s => s.id == id);
}

export type PlaceFavouritesHandler = {
    placeFavourites: () => Promise<PlaceList>;
    addPlaceFavourite: (place: Place.AsObject) => Promise<PlaceList>;
    removePlaceFavourite: (place: Place.AsObject) => Promise<PlaceList>;
}

export const RemotePlaceFavouritesHandler: PlaceFavouritesHandler = {
    addPlaceFavourite: place => DEFAULT_PLACE_FAVOURITES_LOADER.add(place.id),
    removePlaceFavourite: place => DEFAULT_PLACE_FAVOURITES_LOADER.remove(place.id),
    placeFavourites: () => DEFAULT_PLACE_FAVOURITES_LOADER.get()
};