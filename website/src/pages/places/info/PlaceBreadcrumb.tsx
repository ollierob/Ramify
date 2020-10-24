import {Place, PlaceHierarchy} from "../../../protobuf/generated/place_pb";
import {SubMenu} from "../../SubMenu";
import * as React from "react";
import {placeHref} from "../PlaceLinks";
import {PlacesIcon} from "../../../components/images/Icons";

type Props = {
    loading?: boolean;
    place: Place.AsObject;
    hierarchies: ReadonlyArray<PlaceHierarchy.AsObject>
    showType?: boolean
}

export class PlaceBreadcrumb extends React.PureComponent<Props> {

    render() {
        //TODO selector if multiple
        return <SubMenu>
            <div className="places">
                <PlacesIcon style={{marginRight: 8}}/>
                {this.props.hierarchies.length >= 1 && <Breadcrumbs {...this.props} hierarchy={this.props.hierarchies[0]}/>}
            </div>
        </SubMenu>;
    }

}

function listHierarchy(place: Place.AsObject, places: ReadonlyArray<Place.AsObject>, max: number): ReadonlyArray<Place.AsObject> {
    if (!place || !places || !places.length) return [];
    if (places.length == 1) return places;
    const topDown = [place, ...places];
    topDown.reverse();
    if (topDown.length <= max + 1) return topDown;
    return [topDown[0], null].concat(topDown.slice(topDown.length - max));
}

const Breadcrumbs = (props: {place: Place.AsObject, hierarchy: PlaceHierarchy.AsObject, showType?: boolean}) => {
    const places = listHierarchy(props.place, props.hierarchy.placeList, 7);
    return <>
        {places.map((place, i) => <Breadcrumb
            key={place ? place.id : i}
            place={place}
            link={i < places.length - 1}
            separator={i < places.length - 1}
            showType={props.showType || i < places.length - 1}/>)}
    </>;
};

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