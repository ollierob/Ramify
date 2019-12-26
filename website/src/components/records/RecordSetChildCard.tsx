import {ExternalRecordReference, RecordSet} from "../../protobuf/generated/record_pb";
import {Card} from "antd";
import {recordSetHref} from "../../pages/records/RecordLinks";
import {formatYearRange} from "../date/DateFormat";
import * as React from "react";
import {Link} from "../style/Links";
import {RecordSetTitling} from "./RecordSetChildCards";
import {recordTypeName} from "./RecordType";

export const RecordSetChildCard = (props: {record: RecordSet.AsObject} & RecordSetTitling) => {

    const record = props.record;
    let title = props.shortTitle ? shortTitle(record) : record.longtitle;
    if (props.removePrefix && title.startsWith(props.removePrefix)) title = title.substring(props.removePrefix.length).trimLeft();

    const enabled = record.numindividuals > 0;

    return <Card
        title={enabled ? <a href={recordSetHref(record)}>{title}</a> : <span className="disabled">{title}</span>}
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

function shortTitle(record: RecordSet.AsObject): string {
    if (record.shorttitle) return record.shorttitle;
    const name = recordTypeName(record);
    if (name == "Mixed") return record.longtitle;
    return name;
}