import * as React from "react";
import {PlaceList} from "../../../places/Place";
import {Loading} from "../../../style/Loading";
import {NoData} from "../../../style/NoData";
import {List} from "antd";
import {Place} from "../../../../protobuf/generated/place_pb";
import {PlaceLink} from "../../../places/PlaceLink";

export const PlaceFavouritesList = (props: {favourites: PlaceList, loading: boolean}) => {
    if (props.loading) return <Loading/>;
    const favourites = props.favourites;
    if (!favourites || !favourites.length) return <NoData text="No favourite places"/>;
    return <List
        className="placeFavouritesList"
        dataSource={[...favourites]}
        renderItem={place => <PlaceListItem key={place.id} place={place}/>}/>;
};

const PlaceListItem = (props: {place: Place.AsObject}) => {
    return <List.Item>
        <PlaceLink place={props.place} showType/>
    </List.Item>;
};