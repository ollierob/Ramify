import {PlaceId} from "./Place";
import {Place, PlaceBundle, PlaceGroup, PlaceList} from "../../protobuf/generated/place_pb";
import {protoGet} from "../fetch/ProtoFetch";
import {queryParameters} from "../fetch/Fetch";
import {Position} from "../../protobuf/generated/location_pb";
import {PlaceGroupId, ResolvedPlaceGroup} from "./PlaceGroup";

export interface PlaceLoader {

    loadGroup(id: PlaceGroupId): Promise<PlaceGroup.AsObject>

    loadResolvedGroup(groupId: PlaceGroupId): Promise<ResolvedPlaceGroup>

    loadPlace(id: PlaceId): Promise<Place.AsObject>

    loadChildren(id: PlaceId, maxDepth?: number): Promise<ReadonlyArray<Place.AsObject>>

    loadPosition(id: PlaceId): Promise<Position.AsObject>

    loadPlaceBundle(id: PlaceId): Promise<PlaceBundle.AsObject>

    loadCountries(): Promise<ReadonlyArray<Place.AsObject>>

}

class ProtoPlaceLoader implements PlaceLoader {

    loadGroup(id: PlaceGroupId) {
        return protoGet("/places/group/at/" + id, PlaceGroup.deserializeBinary)
            .then(p => p ? p.toObject() : null);
    }

    loadResolvedGroup(id: PlaceGroupId) {
        if (!id) return Promise.reject("No group ID provided");
        //FIXME offer this server-side
        return this.loadGroup(id).then(group => this.resolveGroup(group));
    }

    private resolveGroup(group: PlaceGroup.AsObject): Promise<ResolvedPlaceGroup> {
        const loadChildren = Promise.all([group.defaultchildid].concat(group.otherchildidList).map(this.loadPlaceBundle));
        return loadChildren.then(children => ({group, children}));
    }

    loadPlace(id: PlaceId) {
        return protoGet("/places/at/" + id, Place.deserializeBinary)
            .then(p => p ? p.toObject() : null);
    }

    loadChildren(id: string, maxDepth?: number) {
        return protoGet("/places/children/" + id + queryParameters({depth: maxDepth}), PlaceList.deserializeBinary)
            .then(l => l ? l.getPlaceList().map(p => p.toObject()) : []);
    }

    loadPosition(id: string) {
        return protoGet("/places/position/" + id, Position.deserializeBinary)
            .then(p => p ? p.toObject() : null);
    }

    loadPlaceBundle(id: string): Promise<PlaceBundle.AsObject> {
        return protoGet("/places/bundle/" + id, PlaceBundle.deserializeBinary)
            .then(b => b ? b.toObject() : null);
    }

    loadCountries() {
        return protoGet("/places/countries", PlaceList.deserializeBinary)
            .then(places => places ? places.getPlaceList().map(p => p.toObject()) : []);
    }

}

export const DEFAULT_PLACE_LOADER: PlaceLoader = new ProtoPlaceLoader();