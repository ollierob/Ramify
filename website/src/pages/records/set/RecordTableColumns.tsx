import * as React from "react";
import {RecordSet} from "../../../protobuf/generated/record_pb";
import {ColumnProps} from "antd/es/table";
import {Button, Icon} from "antd";
import {nameToString} from "../../../components/people/Name";
import {FormattedDateRange, FormattedYearRange} from "../../../components/date/FormattedDateRange";
import {PlaceContextMenu} from "../../../components/places/PlaceContextMenu";
import {TableRow, RecordProperties} from "./RecordTable";
import {recordTypeFromValue} from "../../../components/records/RecordType";

export type RecordColumn = ColumnProps<TableRow>;

export function determineColumns(recordSet: RecordSet.AsObject, properties: RecordProperties, showRecordSet?: boolean): RecordColumn[] {
    if (!recordSet) return [];
    const type = recordTypeFromValue(recordSet.type);
    const columns = [...DefaultColumns];
    switch (type) {
        case "BURIAL":
            columns.push(...burialColumns(properties));
            break;
        default:
            columns.push(...genericColumns(properties));
            if (showRecordSet) columns.push(RecordSetColumn);
            break;
    }
    columns.push(NotesColumn);
    return columns;
}

function genericColumns(properties: RecordProperties) {
    const columns: RecordColumn[] = [];
    if (properties.hasBirth) columns.push(BirthYearColumn);
    if (properties.hasResidence) columns.push(ResidenceYearColumn, ResidencePlaceColumn);
    if (properties.hasDeath) columns.push(DeathDateColumn);
    if (properties.hasBurial) columns.push(BurialDateColumn);
    return columns;
}

function burialColumns(properties: RecordProperties) {
    const columns: RecordColumn[] = [];
    if (properties.hasBirth) columns.push(BirthYearColumn);
    if (properties.hasResidence) columns.push(ResidencePlaceColumn);
    if (properties.hasDeath) columns.push(DeathDateColumn);
    if (properties.hasBurial) columns.push(BurialDateColumn);
    return columns;
}

const ImageColumn: RecordColumn = {
    key: "image",
    className: "image",
    dataIndex: "image",
    render: t => <Button disabled={!t} title={t ? "View source image" : "No source image available"}><Icon type="file-search"/></Button>,
    width: 48,
};

const NameColumn: RecordColumn = {
    key: "name",
    title: "Name",
    dataIndex: "person.name.surname",
    render: (t, r) => {
        if (!r.person.name) return <span className="unimportant">Unknown</span>;
        return <>
            {nameToString(r.person.name)}
            {r.person.name.original && <>
                <div className="small unimportant">{r.person.name.original}</div>
            </>}
        </>;
    },
    sorter: (r1, r2) => nameToString(r1.person.name).localeCompare(nameToString(r2.person.name)),
    width: 200,
};

const BirthYearColumn: RecordColumn = {
    key: "birthDate",
    title: "Birth date",
    dataIndex: "birth.date",
    render: (t, r) => r.birth && <FormattedYearRange date={r.birth.date}/>,
    width: 120
};

const ResidenceYearColumn: RecordColumn = {
    key: "residenceDate",
    title: "Residence date",
    render: (t, r) => r.residence && <FormattedYearRange date={r.residence.date} words={{in: ""}}/>,
    width: 150
};

const ResidencePlaceColumn: RecordColumn = {
    key: "residencePlace",
    title: "Residence",
    render: (t, r) => r.residence && <PlaceContextMenu place={r.residence.place} showType/>,
    width: 200
};

const DeathDateColumn: RecordColumn = {
    key: "deathDate",
    title: "Death date",
    dataIndex: "death.date",
    render: (t, r) => r.death && <FormattedDateRange date={r.death.date} accuracy="day"/>,
    width: 120
};

const BurialDateColumn: RecordColumn = {
    key: "burialDate",
    title: "Burial date",
    dataIndex: "burial.date",
    render: (t, r) => r.burial && <FormattedDateRange date={r.burial.date} accuracy="day"/>,
    width: 120
};

const RecordSetColumn: RecordColumn = {
    key: "recordSet",
    title: "Record set",
    render: (t, r) => r.record.recordSet && r.record.recordSet.longtitle,
    width: 200
};

const NotesColumn: RecordColumn = {
    key: "notes",
    title: "Notes",
    dataIndex: "person.notes",
    sorter: (r1, r2) => (r1.person.notes || "").localeCompare(r2.person.notes || "")
};

const DefaultColumns: ReadonlyArray<RecordColumn> = [ImageColumn, NameColumn];