import * as React from "react";
import {Place} from "../../protobuf/generated/place_pb";
import {Card} from "antd";
import {placeTypeName} from "../places/PlaceType";
import {RecordSet} from "../../protobuf/generated/record_pb";
import {FormattedYearRange} from "../date/FormattedDateRange";
import {placeHref} from "../../pages/places/PlaceLinks";
import {recordSetHref} from "../../pages/records/RecordLinks";
import {stringMultimap} from "../Maps";
import "./RecordCards.css";
import {RecordsIcon} from "../images/Icons";

export const RecordCards = (props: {records: ReadonlyArray<RecordSet.AsObject>, groupByParent?: boolean, alsoSee?: ReadonlyArray<Place.AsObject>}) => {
    const records = props.records;
    if (!records || !records.length) return null;
    if (props.groupByParent && records.some(r => r.parent)) return <GroupedRecordCards {...props}/>;
    return <div className="recordCards">
        {records.map(record => <RecordCard record={record}/>)}
        {props.alsoSee && <AlsoSeeCard alsoSee={props.alsoSee}/>}
    </div>;
};

const GroupedRecordCards = (props: {records: ReadonlyArray<RecordSet.AsObject>, alsoSee?: ReadonlyArray<Place.AsObject>}) => {
    const records = props.records;
    const parentGroups = stringMultimap(records, record => record.parent ? record.parent.id : "");
    return <div className="recordCards">
        {Object.keys(parentGroups).map(group => <GroupRecordCard records={parentGroups[group]}/>)}
    </div>;
};

const GroupRecordCard = (props: {records: ReadonlyArray<RecordSet.AsObject>}) => {
    const children = <>{props.records.map(record => <RecordCard record={record}/>)}</>;
    const parent = props.records[0].parent;
    if (!parent) return children;
    return <Card
        className="groupCard"
        title={<><RecordsIcon/> <a href={recordSetHref(parent)}>{parent.title}</a></>}>
        {children}
    </Card>;
};

const RecordCard = (props: {record: RecordSet.AsObject}) => {
    const record = props.record;
    return <Card
        title={<a href={recordSetHref(record)}>{record.title}</a>}
        className="recordCard">
        Available <FormattedYearRange date={record.date}/>
        {record.description && <div className="notags">{record.description}</div>}
    </Card>;
};

const AlsoSeeCard = (props: {alsoSee: ReadonlyArray<Place.AsObject>}) => {
    const alsoSee = props.alsoSee;
    if (!alsoSee || !alsoSee.length) return null;
    return <Card title="Also see" className="recordCard">
        <ul>
            {alsoSee.map(place => <li>
                <a href={placeHref(place)}>{place.name}</a>
                {" "}
                ({placeTypeName(place.type)})
            </li>)}
        </ul>
    </Card>;
};