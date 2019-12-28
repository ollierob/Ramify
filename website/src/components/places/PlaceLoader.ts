import {PlaceId} from "./Place";
import {Place, PlaceBundle, PlaceGroup, PlaceList} from "../../protobuf/generated/place_pb";
import {protoGet} from "../fetch/ProtoFetch";
import {queryParameters} from "../fetch/Fetch";
import {Position} from "../../protobuf/generated/location_pb";
import {PlaceGroupId, ResolvedPlaceGroup} from "./PlaceGroup";

export interface PlaceLoader {

    loadGroup(groupId: PlaceGroupId): Promise<ResolvedPlaceGroup>

    loadPlace(id: PlaceId): Promise<Place.AsObject>

    loadChildren(id: PlaceId, maxDepth?: number): Promise<ReadonlyArray<Place.AsObject>>

    loadPosition(id: PlaceId): Promise<Position.AsObject>

    loadPlaceBundle(id: PlaceId): Promise<PlaceBundle.AsObject>

    loadCountries(options?: CountryOptions): Promise<ReadonlyArray<Place.AsObject>>

    findPlaces(name: string, options: SearchOptions): Promise<ReadonlyArray<Place.AsObject>>

}

type CountryOptions = {
    onlyTopLevel?: boolean
}

type SearchOptions = {
    limit?: number
    within?: PlaceId
}

class ProtoPlaceLoader implements PlaceLoader {

    loadGroup(id: PlaceGroupId) {
        return protoGet("/places/group/at/" + id, PlaceGroup.deserializeBinary)
            .then(p => p ? p.toObject() : null)
            .then(g => this.resolveGroup(g));
    }

    private resolveGroup(group: PlaceGroup.AsObject): Promise<ResolvedPlaceGroup> {
        const loadChildren = Promise.all([group.defaultchild.id].concat(group.otherchildrenList.map(c => c.id)).map(this.loadPlaceBundle));
        return loadChildren.then(children => ({group, children: children.filter(c => !!c)}));
    }

    loadPlace(id: PlaceId) {
        return protoGet("/places/at/" + id, Place.deserializeBinary)
            .then(p => p ? p.toObject() : null);
    }

    loadChildren(id: string, maxDepth?: number) {
        return protoGet("/places/children/" + id + queryParameters({depth: maxDepth}), PlaceList.deserializeBinary).then(readPlaces);
    }

    loadPosition(id: string) {
        return protoGet("/places/position/" + id, Position.deserializeBinary)
            .then(p => p ? p.toObject() : null);
    }

    loadPlaceBundle(id: string): Promise<PlaceBundle.AsObject> {
        return protoGet("/places/bundle/" + id, PlaceBundle.deserializeBinary)
            .then(b => b ? b.toObject() : null);
    }

    loadCountries(options: CountryOptions) {
        if (!options) options = {};
        const url = "/places/countries"
            + queryParameters({onlyTopLevel: options.onlyTopLevel});
        return protoGet(url, PlaceList.deserializeBinary).then(readPlaces);
    }

    findPlaces(name: string, options: SearchOptions = {}) {
        const url = "/places/search?name=" + name
            + "&limit=" + (options.limit || 1000)
            + (options.within ? "&within=" + options.within : "");
        return protoGet(url, PlaceList.deserializeBinary).then(readPlaces);
    }

}

function readPlaces(l: PlaceList): ReadonlyArray<Place.AsObject> {
    return l ? l.getPlaceList().map(p => p.toObject()) : [];
}

export const DEFAULT_PLACE_LOADER: PlaceLoader = new ProtoPlaceLoader();