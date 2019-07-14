import * as React from "react";
import {RecordSet} from "../../../protobuf/generated/record_pb";
import {ColumnProps} from "antd/es/table";
import {Button, Icon} from "antd";
import {nameToString} from "../../../components/people/Name";
import {FormattedDateRange, FormattedYearRange} from "../../../components/date/FormattedDateRange";
import {PlaceContextMenu} from "../../../components/places/PlaceContextMenu";
import {IndividualRecord, RecordProperties} from "./RecordTable";
import {recordTypeFromValue} from "../../../components/records/RecordType";

export type RecordColumn = ColumnProps<IndividualRecord>;

export function determineColumns(recordSet: RecordSet.AsObject, properties: RecordProperties): RecordColumn[] {
    const type = recordTypeFromValue(recordSet.type);
    const columns = [...DefaultColumns];
    switch (type) {
        case "BURIAL":
            columns.push(...burialColumns(properties));
            break;
        default:
            columns.push(...genericColumns(properties));
            break;
    }
    columns.push(NotesColumn);
    return columns;
}

function genericColumns(properties: RecordProperties) {
    const columns: RecordColumn[] = [];
    if (properties.hasBirth) columns.push(BirthYearColumn);
    if (properties.hasResidence && !properties.hasDeath && !properties.hasBurial) columns.push(ResidenceYearColumn);
    if (properties.hasResidence) columns.push(ResidencePlaceColumn);
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
    render: t => <Button disabled={!t} title={t ? "View source image" : "No source image available"}><Icon type="file-image"/></Button>,
    width: 30,
};

const NameColumn: RecordColumn = {
    key: "name",
    title: "Name",
    dataIndex: "person.name.surname",
    render: (t, r) => nameToString(r.person.name),
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
    render: (t, r) => r.residence && <FormattedYearRange date={r.residence.date}/>,
    width: 120
};

const ResidencePlaceColumn: RecordColumn = {
    key: "residencePlace",
    title: "Residence",
    render: (t, r) => r.residence && <PlaceContextMenu place={r.residence.place}/>,
    width: 120
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

const NotesColumn: RecordColumn = {
    key: "notes",
    title: "Notes",
    dataIndex: "person.notes"
};

const DefaultColumns: ReadonlyArray<RecordColumn> = [ImageColumn, NameColumn];