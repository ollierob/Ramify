import * as React from "react";
import {ColumnProps} from "antd/es/table";
import {FamilyRecord} from "./FamilyRecord";
import {Button, Icon} from "antd";
import {recordTypeName} from "../../../../components/records/RecordType";
import {FormattedDateRange} from "../../../../components/date/FormattedDateRange";
import {renderFamilyRecord} from "./FamilyRecordTableCell";

export type FamilyRecordColumn = ColumnProps<FamilyRecord>;

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
    dataIndex: "type",
    render: t => recordTypeName(t),
    width: 100
};

const DateColumn: FamilyRecordColumn = {
    key: "date",
    title: "Date",
    render: (t, r) => <FormattedDateRange date={r.date} accuracy="day"/>,
    width: 120
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

export const FamilyRecordColumns: ReadonlyArray<FamilyRecordColumn> = [ImageColumn, TypeColumn, DateColumn, FamilyColumn, RecordSetColumn];
