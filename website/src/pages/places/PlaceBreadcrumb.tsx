import {Place, PlaceTypeMap} from "../../protobuf/generated/place_pb";
import {SubMenu} from "../SubMenu";
import * as React from "react";
import {placeTypeName} from "../../components/places/PlaceType";
import {placeHref} from "./PlaceLinks";

export const PlaceBreadcrumb = (props: {loading: boolean, place: Place.AsObject}) => {
    return <SubMenu>
        {props.place && <Hierarchy {...props}/>}
    </SubMenu>
};

const Hierarchy = (props: {place: Place.AsObject}) => {

    const hierarchy = listHierarchy(props.place, 7);

    if (!hierarchy.length) return null;

    return <div className="places">
        {hierarchy.map((place, i) => <Breadcrumb place={place} separator={i < hierarchy.length - 1}/>)}
    </div>


};

function listHierarchy(place: Place.AsObject, max: number): ReadonlyArray<Place.AsObject> {
    let list: Place.AsObject[] = [];
    while (place != null) {
        list.push(place);
        place = place.parent;
    }
    list = list.reverse();
    if (list.length <= max + 1) return list;
    return [list[0], null].concat(list.slice(list.length - max))
}

const Breadcrumb = (props: {place: Place.AsObject, separator: boolean}) => {

    const place = props.place;
    if (!place) return <> ... &raquo; </>;

    return <>
        <span className="place">
        {typeBreadcrumb(place.type)}
            <a href={placeHref(place)}>
                <b>{place.name}</b>
            </a>
        </span>
        {props.separator && " » "}
    </>

};

function typeBreadcrumb(type: PlaceTypeMap[keyof PlaceTypeMap]) {
    return placeTypeName(type) + " of ";
}