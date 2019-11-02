import * as React from "react";
import {RecordSet} from "../../../protobuf/generated/record_pb";
import {ColumnProps} from "antd/es/table";
import {Button, Icon} from "antd";
import {nameToString} from "../../../components/people/Name";
import {FormattedDateRange, FormattedYearRange} from "../../../components/date/FormattedDateRange";
import {PlaceLink} from "../../../components/places/PlaceLink";
import {RecordProperties} from "./IndividualRecordTable";
import {recordTypeFromValue} from "../../../components/records/RecordType";
import {IndividualRecord} from "./IndividualRecord";
import {placeTypeName} from "../../../components/places/PlaceType";
import {ColumnSubstringLocalSearch} from "../../../components/table/ant/ColumnSubstringLocalSearch";

export type IndividualRecordColumn = ColumnProps<IndividualRecord>;

export function determineColumns(recordSet: RecordSet.AsObject, properties: RecordProperties, showRecordSet?: boolean): IndividualRecordColumn[] {
    if (!recordSet) return [];
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
    if (showRecordSet) columns.push(RecordSetColumn);
    return columns;
}

function genericColumns(properties: RecordProperties) {
    const columns: IndividualRecordColumn[] = [];
    if (properties.hasBirth) columns.push(BirthYear);
    if (properties.hasBaptism) columns.push(BaptismYear);
    if (properties.hasResidence) columns.push(ResidenceYear, ResidencePlace);
    if (properties.hasDeath) columns.push(DeathDateColumn);
    if (properties.hasBurial) columns.push(BurialDateColumn);
    return columns;
}

function burialColumns(properties: RecordProperties) {
    const columns: IndividualRecordColumn[] = [];
    if (properties.hasBirth) columns.push(BirthYear);
    if (properties.hasResidence) columns.push(ResidenceYear, ResidencePlace);
    if (properties.hasDeath) columns.push(DeathDateColumn);
    if (properties.hasBurial) columns.push(BurialDateColumn);
    return columns;
}

const ImageColumn: IndividualRecordColumn = {
    key: "image",
    className: "image",
    dataIndex: "image",
    render: t => <Button disabled={!t} title={t ? "View source image" : "No source image available"}><Icon type="file-search"/></Button>,
    width: 48,
};

const NameColumn: IndividualRecordColumn = {
    key: "name",
    title: "Name",
    dataIndex: "person.name.surname",
    render: (t, r) => {
        if (!r.person.name) return <span className="unimportant">Unknown</span>;
        return <>
            {nameToString(r.person.name)}
            {r.person.name.original && <Minor text={r.person.name.original}/>}
        </>;
    },
    sorter: (r1, r2) => nameToString(r1.person.name).localeCompare(nameToString(r2.person.name)),
    width: 200,
    ...ColumnSubstringLocalSearch(r => nameToString(r.person.name))
};

const BirthYear: IndividualRecordColumn = {
    key: "birthDate",
    title: "Birth date",
    dataIndex: "birth.date",
    render: (t, r) => r.birth && <FormattedYearRange date={r.birth.date}/>,
    width: 120
};

const BaptismYear: IndividualRecordColumn = {
    key: "baptismDate",
    title: "Baptism date",
    dataIndex: "baptism.date",
    render: (t, r) => r.baptism && <FormattedYearRange date={r.baptism.date}/>,
    width: 150
};

const ResidenceYear: IndividualRecordColumn = {
    key: "residenceDate",
    title: "Residence date",
    render: (t, r) => r.residence && <FormattedYearRange date={r.residence.date} words={{in: ""}}/>,
    width: 150
};

const ResidencePlace: IndividualRecordColumn = {
    key: "residencePlace",
    title: "Residence",
    render: (t, r) => r.residence && <>
        <PlaceLink place={r.residence.place}/>
        {r.residence.place && <Minor text={placeTypeName(r.residence.place.type)}/>}
    </>,
    width: 200,
    ...ColumnSubstringLocalSearch(r => r.residence.place && r.residence.place.name)
};

const DeathDateColumn: IndividualRecordColumn = {
    key: "deathDate",
    title: "Death date",
    dataIndex: "death.date",
    render: (t, r) => r.death && <FormattedDateRange date={r.death.date} accuracy="day"/>,
    width: 120
};

const BurialDateColumn: IndividualRecordColumn = {
    key: "burialDate",
    title: "Burial date",
    dataIndex: "burial.date",
    render: (t, r) => r.burial && <FormattedDateRange date={r.burial.date} accuracy="day"/>,
    width: 120
};

const RecordSetColumn: IndividualRecordColumn = {
    key: "recordSet",
    title: "Record set",
    render: (t, r) => r.record.recordSet && r.record.recordSet.longtitle,
    width: 200
};

const NotesColumn: IndividualRecordColumn = {
    key: "notes",
    title: "Notes",
    dataIndex: "person.notes",
    sorter: (r1, r2) => (r1.person.notes || "").localeCompare(r2.person.notes || ""),
    ...ColumnSubstringLocalSearch(r => r.person.notes)
};

const DefaultColumns: ReadonlyArray<IndividualRecordColumn> = [ImageColumn, NameColumn];

const Minor = (props: {text: string}) => <div className="small unimportant">{props.text}</div>;