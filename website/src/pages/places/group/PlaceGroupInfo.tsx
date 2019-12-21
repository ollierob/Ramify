import * as React from "react";
import {AsyncData} from "../../../components/fetch/AsyncData";
import {Place, PlaceHistory} from "../../../protobuf/generated/place_pb";
import {Card, Tabs} from "antd";
import {PlaceId} from "../../../components/places/Place";
import {ResolvedPlaceGroup, sortPlaceGroupChildren} from "../../../components/places/PlaceGroup";
import {PlaceFavouritesHandler} from "../../../components/places/PlaceFavourites";
import {PlaceGroupTab} from "./PlaceGroupTab";
import {Flag} from "../../../components/images/Flag";
import {formatYearRanges} from "../../../components/date/DateFormat";

type Props = {
    group: AsyncData<ResolvedPlaceGroup>
    selected: PlaceId;
    select: (id: PlaceId) => void;
    favourites: PlaceFavouritesHandler
}

export const PlaceGroupInfo = (props: Props) => {

    const group = props.group.data;
    if (!group) return null;

    return <Card
        className="placeGroup"
        title={<><Flag iso={group.iso || group.children[0]?.place.iso}/><b>{group.group.name}</b></>}>

        <Tabs
            size="large"
            activeKey={props.selected}
            onChange={active => props.select(active)}>

            {sortPlaceGroupChildren(group.group, group.children).map(child => <Tabs.TabPane key={child.place.id} tab={<Title place={child.place}/>}>
                <PlaceGroupTab
                    key={child.place.id}
                    group={group.group}
                    favourites={props.favourites}
                    place={child}/>
            </Tabs.TabPane>)}

        </Tabs>

    </Card>;

};

const Title = (props: {place: Place.AsObject}) => {
    const place = props.place;
    return <>
        {place.type.name}
        <History history={place.history}/>
    </>;
};

const History = (props: {history: PlaceHistory.AsObject}) => {
    const history = props.history;
    if (!history) return null;
    if (!history.open && !history.close) return null;
    return <span className="history">
        {" "}
        ({formatYearRanges(history.open, history.close, {after: "since ", before: "until "})})
    </span>;
};

