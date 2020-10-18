import {Place} from "../../../protobuf/generated/place_pb";
import {SubMenu} from "../../SubMenu";
import * as React from "react";
import {placeHref} from "../PlaceLinks";
import {PlacesIcon} from "../../../components/images/Icons";

export const PlaceBreadcrumb = (props: {loading?: boolean, place: Place.AsObject, parents: ReadonlyArray<Place.AsObject>, showType?: boolean}) => {

    const hierarchy = listHierarchy(props.place, props.parents, 7);
    if (hierarchy.length <= 1) return null;

    return <SubMenu>
        <div className="places">
            <PlacesIcon style={{marginRight: 8}}/>
            {hierarchy.map((place, i) => <Breadcrumb
                key={place ? place.id : i}
                place={place}
                link={i < hierarchy.length - 1}
                separator={i < hierarchy.length - 1}
                showType={props.showType || i < hierarchy.length - 1}/>)}
        </div>
    </SubMenu>;

};

function listHierarchy(place: Place.AsObject, parents: ReadonlyArray<Place.AsObject>, max: number): ReadonlyArray<Place.AsObject> {
    if (!place || !parents || !parents.length) return [];
    if (parents.length == 1) return parents;
    const topDown = [place, ...parents];
    topDown.reverse();
    if (topDown.length <= max + 1) return topDown;
    return [topDown[0], null].concat(topDown.slice(topDown.length - max));
}

const Breadcrumb = (props: {place: Place.AsObject, separator: boolean, showType: boolean, link?: boolean}) => {

    const place = props.place;
    if (!place) return <> ... &raquo; </>;

    const type = place.type;
    const prefix = props.showType && place.type.canprefix && !place.name.endsWith(type.name);
    const suffix = !prefix && props.showType && place.type.cansuffix && !place.name.endsWith(type.name);

    return <>
        <span className="place">
            {prefix && <>{type.name} of </>}
            {props.link
                ? <a href={placeHref(place)}><b>{place.name}</b></a>
                : place.name}
            {suffix && <> {type.name}</>}
        </span>
        {props.separator && " » "}
    </>;

};