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
    if (properties.hasMention) columns.push(MentionYear);
    if (properties.hasDeath) columns.push(DeathDateColumn, DeathAgeColumn);
    if (properties.hasBurial) columns.push(BurialDateColumn);
    return columns;
}

function burialColumns(properties: RecordProperties) {
    const columns: IndividualRecordColumn[] = [];
    if (properties.hasBirth) columns.push(BirthYear);
    if (properties.hasResidence) columns.push(ResidenceYear, ResidencePlace);
    if (properties.hasMention) columns.push(MentionYear);
    if (properties.hasDeath) columns.push(DeathDateColumn, DeathAgeColumn);
    if (properties.hasBurial) columns.push(BurialDateColumn);
    return columns;
}

const ImageColumn: IndividualRecordColumn = {
    key: "image",
    className: "image",
    dataIndex: "image",
    render: t => <Button disabled={!t} title={t ? "View source image" : "No source image available"}><Icon type="file-image"/></Button>,
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
            {r.person.name.original && <Minor children={r.person.name.original}/>}
        </>;
    },
    sorter: (r1, r2) => nameToString(r1.person.name).localeCompare(nameToString(r2.person.name)),
    width: 200,
    ...ColumnSubstringLocalSearch(r => nameToString(r.person.name))
};

const BirthYear: IndividualRecordColumn = {
    key: "birthDate",
    title: "Born",
    dataIndex: "birth.date",
    render: (t, r) => r.birth && <>
        <FormattedYearRange date={r.birth.date}/>
    </>,
    width: 110
};

const BaptismYear: IndividualRecordColumn = {
    key: "baptismDate",
    title: "Baptized",
    dataIndex: "baptism.date",
    render: (t, r) => r.baptism && <FormattedYearRange date={r.baptism.date}/>,
    width: 150
};

const ResidenceYear: IndividualRecordColumn = {
    key: "residenceDate",
    title: "Residence date",
    render: (t, r) => r.residence && r.residence.length > 0 && <FormattedYearRange date={r.residence[0].date} words={{in: ""}}/>,
    width: 150
};

const ResidencePlace: IndividualRecordColumn = {
    key: "residencePlace",
    title: "Residence",
    render: (t, r) => r.residence && r.residence.length > 0 && r.residence[0].place && <>
        <PlaceLink place={r.residence[0].place}/>
        <Minor>{placeTypeName(r.residence[0].place.type)}</Minor>
    </>,
    width: 200,
    ...ColumnSubstringLocalSearch(r => r.residence && r.residence.length && r.residence[0].place && r.residence[0].place.name)
};

const MentionYear: IndividualRecordColumn = {
    key: "mentionDate",
    title: "Mentioned",
    render: (t, r) => r.mention && r.mention.length > 0 && <FormattedYearRange date={r.mention[0].date} words={{in: ""}}/>,
    width: 150
};

const DeathDateColumn: IndividualRecordColumn = {
    key: "deathDate",
    title: "Died",
    dataIndex: "death.date",
    render: (t, r) => r.death && <FormattedDateRange date={r.death.date} accuracy="day"/>,
    width: 120
};

const DeathAgeColumn: IndividualRecordColumn = {
    key: "deathAge",
    title: "Age",
    dataIndex: "death.givenage",
    render: (t, r) => r.death && r.death.givenage > 0 && <>{r.death.givenage}</>,
    sorter: (r1, r2) => (r1.death ? r1.death.givenage : 0) - (r2.death ? r2.death.givenage : 0),
    width: 80
};

const BurialDateColumn: IndividualRecordColumn = {
    key: "burialDate",
    title: "Buried",
    dataIndex: "burial.date",
    render: (t, r) => r.burial && <FormattedDateRange date={r.burial.date} accuracy="day"/>,
    width: 110
};

const RecordSetColumn: IndividualRecordColumn = {
    key: "recordSet",
    title: "Record set",
    render: (t, r) => r.record.recordSet && r.record.recordSet.longtitle,
    width: 220
};

const NotesColumn: IndividualRecordColumn = {
    key: "notes",
    title: "Notes",
    dataIndex: "person.notes",
    sorter: (r1, r2) => (r1.person.notes || "").localeCompare(r2.person.notes || ""),
    ...ColumnSubstringLocalSearch(r => r.person.notes)
};

const DefaultColumns: ReadonlyArray<IndividualRecordColumn> = [ImageColumn, NameColumn];

const Minor = (props: {children: React.ReactNode}) => <div className="small unimportant">{props.children}</div>;