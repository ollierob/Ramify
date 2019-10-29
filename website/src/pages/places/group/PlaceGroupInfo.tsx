import * as React from "react";
import {AsyncData} from "../../../components/fetch/AsyncData";
import {Place, PlaceBundle} from "../../../protobuf/generated/place_pb";
import {Card, Tabs} from "antd";
import {PlaceId} from "../../../components/places/Place";
import {ResolvedPlaceGroup} from "../../../components/places/PlaceGroup";
import {placeTypeKey, placeTypeName, sortByPlaceType} from "../../../components/places/PlaceType";
import {PlaceInfo} from "../info/PlaceInfo";
import {PlaceFavouritesHandler} from "../../../components/places/PlaceFavourites";
import {PlaceBreadcrumb} from "../info/PlaceBreadcrumb";

type Props = {
    group: AsyncData<ResolvedPlaceGroup>
    selected: PlaceId;
    select: (id: PlaceId) => void;
    favourites: PlaceFavouritesHandler
}

export const PlaceGroupInfo = (props: Props) => {

    const resolved = props.group.data;
    if (!resolved) return null;

    return <Card
        className="placeGroup"
        title={<b>{resolved.group.name}</b>}>

        <Tabs
            size="large"
            activeKey={props.selected}
            onChange={active => props.select(active)}>

            {sortChildren(resolved.children).map(child => <Tabs.TabPane key={child.place.id} tab={placeTypeName(child.place.type)}>
                <ChildInfo favourites={props.favourites} place={child}/>
            </Tabs.TabPane>)}

        </Tabs>

    </Card>;

};

function sortChildren(children: ReadonlyArray<PlaceBundle.AsObject>): ReadonlyArray<PlaceBundle.AsObject> {
    if (!children || !children.length) return [];
    const sorted = [...children];
    sorted.sort((p1, p2) => sortByPlaceType(placeTypeKey(p1.place.type), placeTypeKey(p2.place.type)));
    return sorted;
}

type ChildProps = {
    place: PlaceBundle.AsObject;
    favourites: PlaceFavouritesHandler
}

class ChildInfo extends React.PureComponent<ChildProps> {

    render() {

        const place = this.props.place;
        if (!place) return null;

        return <>
            <PlaceBreadcrumb place={place.place}/>
            <PlaceInfo
                {...this.props.favourites}
                card={false}
                place={place.place}
                description={place.description}
                childPlaces={place.childList}/>
        </>;

    }

}