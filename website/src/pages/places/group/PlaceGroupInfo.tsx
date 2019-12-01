import * as React from "react";
import {AsyncData} from "../../../components/fetch/AsyncData";
import {Place, PlaceBundle, PlaceHistory} from "../../../protobuf/generated/place_pb";
import {Card, Tabs} from "antd";
import {PlaceId} from "../../../components/places/Place";
import {ResolvedPlaceGroup} from "../../../components/places/PlaceGroup";
import {placeTypeKey, placeTypeName, sortByPlaceType} from "../../../components/places/PlaceType";
import {PlaceFavouritesHandler} from "../../../components/places/PlaceFavourites";
import {earliestYear, latestYear, yearPeriod} from "../../../components/date/DateRange";
import {PlaceGroupTab} from "./PlaceGroupTab";
import {Flag} from "../../../components/images/Flag";
import {DateRange} from "../../../protobuf/generated/date_pb";

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
        title={<><Flag iso={resolved.iso}/><b>{resolved.group.name}</b></>}>

        <Tabs
            size="large"
            activeKey={props.selected}
            onChange={active => props.select(active)}>

            {sortChildren(resolved.children).map(child => <Tabs.TabPane key={child.place.id} tab={<Title place={child.place}/>}>
                <PlaceGroupTab
                    key={child.place.id}
                    group={resolved.group}
                    favourites={props.favourites}
                    place={child}/>
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

const Title = (props: {place: Place.AsObject}) => {
    const place = props.place;
    return <>
        {placeTypeName(place.type)}
        <History history={place.history}/>
    </>;
};

const History = (props: {history: PlaceHistory.AsObject}) => {
    const history = props.history;
    if (!history) return null;
    if (!history.open && !history.close) return null;
    return <span className="history">
        {" "}
        ({yearPeriod(history.open, history.close)})
    </span>;
};

