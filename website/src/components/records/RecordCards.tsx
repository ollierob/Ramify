import * as React from "react";
import {Place} from "../../protobuf/generated/place_pb";
import {Card} from "antd";
import {placeTypeName} from "../places/PlaceType";
import {ExternalRecordReference, RecordSet} from "../../protobuf/generated/record_pb";
import {placeHref} from "../../pages/places/PlaceLinks";
import {recordSetHref} from "../../pages/records/RecordLinks";
import "./RecordCards.css";
import {recordTypeName} from "./RecordType";
import {HasClass} from "../style/HasClass";
import {Link} from "../style/Links";
import {Loading} from "../style/Loading";
import {sortRecordSetByTitle} from "./RecordSet";
import {formatYearRange} from "../date/DateFormat";

type Titling = {
    shortTitle?: boolean;
    removePrefix?: string;
}

type Props = HasClass & Titling & {
    loading?: boolean
    records: ReadonlyArray<RecordSet.AsObject>
    alsoSee?: ReadonlyArray<Place.AsObject>
    fixedWidth?: boolean;
    ignoreNone?: boolean;
}

export const RecordCards = (props: Props) => {

    if (!props.records) return null;

    const records = [...props.records].sort(sortRecordSetByTitle);
    //if (props.groupByParent && props.relatives && props.relatives.length) return <GroupedRecordCards {...props}/>;

    return <div
        className={"recordCards" + (props.fixedWidth ? " fixedWidth" : "")}
        style={props.style}>
        {props.loading && <Loading/>}
        {records.map(record => <RecordCard {...props} key={record.id} record={record}/>)}
        {!props.loading && !records.length && !props.ignoreNone && <NoRecordCards/>}
        {props.alsoSee && <AlsoSeeCard alsoSee={props.alsoSee}/>}
    </div>;

};

const RecordCard = (props: {record: RecordSet.AsObject} & Titling) => {
    const record = props.record;
    let title = props.shortTitle ? shortTitle(record) : record.longtitle;
    if (props.removePrefix && title.startsWith(props.removePrefix)) title = title.substring(props.removePrefix.length).trimLeft();
    return <Card
        title={<a href={recordSetHref(record)}>{title}</a>}
        className="recordCard">
        Available {formatYearRange(record.date)}
        <br/>
        {record.numrecords.toLocaleString()} records, {record.numindividuals.toLocaleString()} individuals
        {record.externalreferenceList.map(ref => <RecordReference key={ref.reference} reference={ref}/>)}
        {record.description && <div className="unimportant">{record.description}</div>}
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
    const name = recordTypeName(record);
    if (name == "Mixed") return record.longtitle;
    return name;
}

export const NoRecordCards = () => {
    return <>No records known.</>;
};
