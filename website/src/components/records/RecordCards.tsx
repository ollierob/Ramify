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
import {RecordType, recordTypeFromValue} from "./RecordType";

export const RecordCards = (props: {records: ReadonlyArray<RecordSet.AsObject>, groupByParent?: boolean, shortTitle?: boolean, alsoSee?: ReadonlyArray<Place.AsObject>}) => {
    const records = props.records;
    if (!records || !records.length) return null;
    if (props.groupByParent && records.some(r => r.parent)) return <GroupedRecordCards {...props}/>;
    return <div className="recordCards">
        {records.map(record => <RecordCard record={record} shortTitle={props.shortTitle}/>)}
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
    const children = <>{props.records.map(record => <RecordCard record={record} shortTitle/>)}</>;
    const parent = props.records[0].parent;
    if (!parent) return children;
    return <Card
        className="groupCard"
        title={<><RecordsIcon/> <a href={recordSetHref(parent)}>{parent.longtitle}</a></>}>
        {children}
    </Card>;
};

const RecordCard = (props: {record: RecordSet.AsObject, shortTitle?: boolean}) => {
    const record = props.record;
    const title = props.shortTitle ? (record.shorttitle || ShortRecordTitles[recordTypeFromValue(record.type)]) : record.longtitle;
    return <Card
        title={<a href={recordSetHref(record)}>{title}</a>}
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

const ShortRecordTitles: { [key in RecordType] } = {
    BAPTISM: "Baptisms",
    BIRTH: "Births",
    BURIAL: "Burials",
    DEATH: "Deaths",
    MARRIAGE: "Marriages",
    MEMBERSHIP: "Memberships",
    MEMORIAL: "Memorials",
    MENTION: "Mentions",
    MIXED: "Mixed",
    PAYMENT: "Payments",
    PROBATE: "Probate",
    RESIDENCE: "Residence",
    WILL: "Wills"
};