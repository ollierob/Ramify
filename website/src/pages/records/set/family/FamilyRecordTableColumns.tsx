import * as React from "react";
import {ColumnProps} from "antd/es/table";
import {FamilyRecord} from "./FamilyRecord";
import {Button, Icon} from "antd";
import {recordTypeName} from "../../../../components/records/RecordType";
import {FormattedDateRange} from "../../../../components/date/FormattedDateRange";
import {renderFamilyRecord} from "./FamilyRecordTableCell";
import {RecordSet} from "../../../../protobuf/generated/record_pb";
import {PlaceLink} from "../../../../components/places/PlaceLink";
import {placeTypeName} from "../../../../components/places/PlaceType";

export type FamilyRecordColumn = ColumnProps<FamilyRecord>;

export function determineColumns(recordSet: Readonly<RecordSet.AsObject>, records: ReadonlyArray<FamilyRecord>): FamilyRecordColumn[] {
    const columns = [...PrefixColumns];
    if (records.some(r => !!r.place)) columns.push(PlaceColumn);
    return columns.concat(SuffixColumns);
}

const ImageColumn: FamilyRecordColumn = {
    key: "image",
    className: "image",
    dataIndex: "image",
    render: t => <Button disabled={!t} title={t ? "View source image" : "No source image available"}><Icon type="file-image"/></Button>,
    width: 48,
};

const TypeColumn: FamilyRecordColumn = {
    key: "type",
    title: "Type",
    render: (t, r) => recordTypeName(r.type),
    width: 100
};

const DateColumn: FamilyRecordColumn = {
    key: "date",
    title: "Date",
    render: (t, r) => <FormattedDateRange date={r.date} accuracy="day"/>,
    width: 120
};

const PlaceColumn: FamilyRecordColumn = {
    key: "place",
    title: "Place",
    render: (t, r) => r.place && <>
        <PlaceLink place={r.place}/>
        <Minor>{placeTypeName(r.place.type)}</Minor>
    </>,
    width: 140
};

const FamilyColumn: FamilyRecordColumn = {
    key: "family",
    title: "Family",
    className: "family",
    render: (t, r) => renderFamilyRecord(r)
};

const RecordSetColumn: FamilyRecordColumn = {
    key: "recordSet",
    title: "Record set",
    render: (t, r) => r.recordSet && r.recordSet.longtitle,
    width: 220
};

const PrefixColumns = [ImageColumn, TypeColumn, DateColumn];
const SuffixColumns = [FamilyColumn, RecordSetColumn];
const Minor = (props: {children: React.ReactNode}) => <div className="small unimportant">{props.children}</div>;