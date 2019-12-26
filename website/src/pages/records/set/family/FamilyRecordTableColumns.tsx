import * as React from "react";
import {ColumnProps} from "antd/es/table";
import {FamilyRecord} from "./FamilyRecord";
import {Button, Icon} from "antd";
import {recordTypeName} from "../../../../components/records/RecordType";
import {renderFamilyRecord} from "./FamilyRecordTableCell";
import {RecordSet} from "../../../../protobuf/generated/record_pb";
import {PlaceLink} from "../../../../components/places/PlaceLink";
import {formatDateRange} from "../../../../components/date/DateFormat";
import {sortDatesByEarliest} from "../../../../components/date/DateRange";

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
    sorter: (r1, r2) => recordTypeName(r1.type).localeCompare(recordTypeName(r2.type)),
    width: 130
};

const DateColumn: FamilyRecordColumn = {
    key: "date",
    title: "Date",
    render: (t, r) => <>{formatDateRange(r.date, "day", {in: ""})}</>,
    sorter: (r1, r2) => sortDatesByEarliest(r1.date, r2.date),
    width: 130
};

const PlaceColumn: FamilyRecordColumn = {
    key: "place",
    title: "Place",
    render: (t, r) => r.place && <>
        <PlaceLink place={r.place}/>
        <Minor>{r.place.type.name}</Minor>
    </>,
    width: 160
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