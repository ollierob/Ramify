import * as React from "react";
import {Place} from "../../protobuf/generated/place_pb";
import {Card} from "antd";
import {placeTypeName} from "../places/PlaceType";
import {ExternalRecordReference, RecordSet, RecordSetRelatives} from "../../protobuf/generated/record_pb";
import {FormattedYearRange} from "../date/FormattedDateRange";
import {placeHref} from "../../pages/places/PlaceLinks";
import {recordSetHref} from "../../pages/records/RecordLinks";
import "./RecordCards.css";
import {RecordsIcon} from "../images/Icons";
import {RecordType, recordTypeFromValue} from "./RecordType";
import {HasClass} from "../style/HasClass";
import {Link} from "../style/Links";
import {Loading} from "../style/Loading";
import {sortRecordSetByDateThenTitle} from "./RecordSet";

type Props = HasClass & {
    loading?: boolean
    records: ReadonlyArray<RecordSet.AsObject>
    //relatives?: ReadonlyArray<RecordSetRelatives.AsObject>
    //groupByParent?: boolean
    shortTitle?: boolean
    alsoSee?: ReadonlyArray<Place.AsObject>;
    noRecordsMessage?: React.ReactNode
    fixedWidth?: boolean;
}

export const RecordCards = (props: Props) => {

    const records = [...props.records].sort(sortRecordSetByDateThenTitle);
    //if (props.groupByParent && props.relatives && props.relatives.length) return <GroupedRecordCards {...props}/>;

    return <div
        className={"recordCards" + (props.fixedWidth ? " fixedWidth" : "")}
        style={props.style}>
        {props.loading && <Loading/>}
        {records.map(record => <RecordCard record={record} shortTitle={props.shortTitle}/>)}
        {!props.loading && !records.length && props.noRecordsMessage}
        {props.alsoSee && <AlsoSeeCard alsoSee={props.alsoSee}/>}
    </div>;

};

// const GroupedRecordCards = (props: {records: ReadonlyArray<RecordSet.AsObject>, alsoSee?: ReadonlyArray<Place.AsObject>}) => {
//     const records = props.records;
//     const parentGroups: CardGrouping[] = [];
//     return <div className="recordCards">
//         {Object.keys(parentGroups).map(group => <GroupRecordCard records={parentGroups[group]}/>)}
//     </div>;
// };

type CardGrouping = {records: ReadonlyArray<RecordSet.AsObject>, relatives: RecordSetRelatives.AsObject}

const GroupRecordCard = (props: {records: ReadonlyArray<RecordSet.AsObject>, relatives: RecordSetRelatives.AsObject}) => {
    const children = [...props.records].sort(sortRecordSetByDateThenTitle);
    const cards = <>{children.map(record => <RecordCard record={record} shortTitle/>)}</>;
    const parent = props.relatives && props.relatives.parent;
    if (!parent) return cards;
    return <Card
        className="groupCard"
        title={<><RecordsIcon/> <a href={recordSetHref(parent)}>{parent.longtitle}</a></>}>
        {cards}
    </Card>;
};

const RecordCard = (props: {record: RecordSet.AsObject, shortTitle?: boolean}) => {
    const record = props.record;
    const title = props.shortTitle ? shortTitle(record) : record.longtitle;
    return <Card
        title={<a href={recordSetHref(record)}>{title}</a>}
        className="recordCard">
        Available <FormattedYearRange date={record.date}/>
        <br/>
        {record.numrecords.toLocaleString()} records
        {record.externalreferenceList.map(ref => <RecordReference reference={ref}/>)}
        {record.description && <div className="notags">{record.description}</div>}
    </Card>;
};

const RecordReference = (props: {reference: ExternalRecordReference.AsObject}) => {
    return <div className="reference">
        <Link link={props.reference.link} iconPath={props.reference.archive.icon} newWindow>
            {props.reference.reference}
        </Link>
    </div>;
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

function shortTitle(record: RecordSet.AsObject): string {
    if (record.shorttitle) return record.shorttitle;
    const type = recordTypeFromValue(record.type);
    if (type == "MIXED") return record.longtitle;
    return ShortRecordTitles[type];

}

const ShortRecordTitles: { [key in RecordType]: string } = {
    UNSPECIFIED: "Miscellaneous",
    BAPTISM: "Baptisms",
    BIRTH: "Births",
    BURIAL: "Burials",
    DEATH: "Deaths",
    MARRIAGE: "Marriages",
    MEMBERSHIP: "Membership",
    MEMORIAL: "Memorials",
    MENTION: "Mentions",
    MIXED: "Mixed",
    PAYMENT: "Payments",
    PROBATE: "Probate",
    RESIDENCE: "Residence",
    WILL: "Wills"
};