import * as React from "react";
import {Place} from "../../protobuf/generated/place_pb";
import {PlaceLink} from "./PlaceLink";

export const PlaceAndParent = (props: {place: Place.AsObject}) => {

    const place = props.place;
    if (!place) return null;

    return <>
        <PlaceAndType place={place}/>
    </>;

    //{place.parent && <>&nbsp;in <PlaceAndType place={place.parent}/></>}

};

const PlaceAndType = (props: {place: Place.AsObject}) => {
    const place = props.place;
    if (!place) return null;
    return <>
        {place.type.canprefix && !place.type.cansuffix && <><span className="unimportant">{place.type.name}</span> of&nbsp;</>}
        <PlaceLink place={place} showType={false}/>
        {place.type.cansuffix && <>&nbsp;<span className="unimportant">{place.type.name}</span></>}
    </>;
};