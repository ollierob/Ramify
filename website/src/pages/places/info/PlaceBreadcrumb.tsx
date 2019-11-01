import {Place} from "../../../protobuf/generated/place_pb";
import {SubMenu} from "../../SubMenu";
import * as React from "react";
import {placeTypeName} from "../../../components/places/PlaceType";
import {placeHref} from "../PlaceLinks";
import {PlacesIcon} from "../../../components/images/Icons";

export const PlaceBreadcrumb = (props: {loading?: boolean, place: Place.AsObject}) => {

    const hierarchy = listHierarchy(props.place, 7);
    if (hierarchy.length <= 1) return null;

    return <SubMenu>
        <div className="places">
            <PlacesIcon style={{marginRight: 8}}/>
            {hierarchy.map((place, i) => <Breadcrumb key={place.id} place={place} separator={i < hierarchy.length - 1} showType={i < hierarchy.length - 1}/>)}
        </div>
    </SubMenu>;

};

function listHierarchy(place: Place.AsObject, max: number): ReadonlyArray<Place.AsObject> {
    if (!place) return [];
    let list: Place.AsObject[] = [];
    while (place != null) {
        list.push(place);
        place = place.parent;
    }
    list = list.reverse();
    if (list.length <= max + 1) return list;
    return [list[0], null].concat(list.slice(list.length - max));
}

const Breadcrumb = (props: {place: Place.AsObject, separator: boolean, showType: boolean}) => {

    const place = props.place;
    if (!place) return <> ... &raquo; </>;

    const type = placeTypeName(place.type);
    const prefix = props.showType && !place.name.endsWith(type) && type != "Country";

    return <>
        <span className="place">
            {prefix && <>{type} of </>}
            <a href={placeHref(place)}>
                <b>{place.name}</b>
            </a>
        </span>
        {props.separator && " » "}
    </>;

};