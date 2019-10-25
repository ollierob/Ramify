import * as React from "react";
import {AsyncData} from "../../../components/fetch/AsyncData";
import {Place, PlaceBundle} from "../../../protobuf/generated/place_pb";
import {Card, Tabs} from "antd";
import {PlaceId} from "../../../components/places/Place";
import {ResolvedPlaceGroup} from "../../../components/places/PlaceGroup";
import {placeTypeName} from "../../../components/places/PlaceType";
import {PlaceInfo} from "../info/PlaceInfo";
import {PlaceFavouritesHandler} from "../../../components/places/PlaceFavourites";

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
        title={resolved.group.name}>

        <Tabs
            size="large"
            activeKey={props.selected}
            onChange={active => props.select(active)}>

            {resolved.children.map(child => <Tabs.TabPane key={child.place.id} tab={placeTypeName(child.place.type)}>
                <PlaceInfo
                    {...props.favourites}
                    place={child.place}
                    description={child.description}
                    childPlaces={child.childList}/>
            </Tabs.TabPane>)}

        </Tabs>

    </Card>;

};