import * as React from "react";
import {RecordSet} from "../../../../protobuf/generated/record_pb";
import {ColumnProps} from "antd/es/table";
import {Button, Icon} from "antd";
import {PlaceLink} from "../../../../components/places/PlaceLink";
import {recordTypeFromValue} from "../../../../components/records/RecordType";
import {IndividualRecord, IndividualRecordProperties} from "./IndividualRecord";
import {ColumnSubstringLocalSearch} from "../../../../components/table/ant/ColumnSubstringLocalSearch";
import {isFemale, isMale} from "../../../../components/people/Gender";
import {EmptyPrefixWords, formatDateRange, formatYearRange} from "../../../../components/date/DateFormat";
import {sortDatesByEarliest} from "../../../../components/date/DateRange";
import {recordSetHref, RecordSetLink} from "../../RecordLinks";
import {sortAges} from "../../../../components/people/Age";
import {DeathDatePlaceCell} from "./IndividualRecordTableCell";
import {PersonName} from "../../../../components/people/PersonName";

export type IndividualRecordColumn = ColumnProps<IndividualRecord>;

export function determineColumns(recordSet: RecordSet.AsObject, properties: IndividualRecordProperties, showRecordSet?: boolean): IndividualRecordColumn[] {
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

function genericColumns(properties: IndividualRecordProperties) {
    const columns: IndividualRecordColumn[] = [];
    if (properties.hasBirth) columns.push(BirthYear);
    if (properties.hasBaptism) columns.push(BaptismYear);
    if (properties.hasResidence) columns.push(ResidencePlace, ResidenceYear);
    if (properties.hasMention) columns.push(MentionYear);
    if (properties.hasDeath) columns.push(DeathDatePlaceColumn);
    if (properties.hasDeath && !properties.hasMention && !properties.hasBirth) columns.push(DeathAgeColumn);
    if (properties.hasBurial) columns.push(BurialDateColumn);
    return columns;
}

function burialColumns(properties: IndividualRecordProperties) {
    const columns: IndividualRecordColumn[] = [];
    if (properties.hasBirth) columns.push(BirthYear);
    if (properties.hasResidence) columns.push(ResidencePlace, ResidenceYear);
    if (properties.hasMention) columns.push(MentionYear);
    if (properties.hasDeath) columns.push(DeathDatePlaceColumn, DeathAgeColumn);
    if (properties.hasBurial) columns.push(BurialDateColumn);
    return columns;
}

const YearColumn: Partial<IndividualRecordColumn> = {
    title: "Date",
    width: 85
};

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
        if (r.person.name && r.person.name.unknown) {
            const gender = isMale(r.person) ? "male" : isFemale(r.person) ? "female" : "";
            return <span className="unimportant">Unknown{gender && <> {gender}</>}</span>;
        }
        return <>
            <PersonName name={r.person.name}/>
            {r.person.name && r.person.name.variationList.map(v => <Minor children={v}/>)}
        </>;
    },
    sorter: (r1, r2) => r1.person.name.value.localeCompare(r2.person.name.value),
    width: 200,
    ...ColumnSubstringLocalSearch(r => r.person.name.value)
};

const BirthYear: IndividualRecordColumn = {
    key: "birthDate",
    title: "Birth year",
    dataIndex: "birth.date",
    render: (t, r) => r.birth && <>{formatYearRange(r.birth.date)}</>,
    width: 100
};

const BaptismYear: IndividualRecordColumn = {
    key: "baptismDate",
    title: "Baptism date",
    dataIndex: "baptism.date",
    render: (t, r) => r.baptism && <>{formatDateRange(r.baptism.date, "month")}</>,
    width: 100
};

const ResidenceYear: IndividualRecordColumn = {
    ...YearColumn,
    key: "residenceDate",
    render: (t, r) => hasResidence(r) && <>{formatDateRange(r.residence[0].date, "month", EmptyPrefixWords)}</>,
    sorter: (r1, r2) => sortDatesByEarliest(hasResidence(r1) && r1.residence[0].date, hasResidence(r2) && r2.residence[0].date),
};

function hasResidence(r: IndividualRecord) {
    return r.residence && r.residence.length > 0;
}

const ResidencePlace: IndividualRecordColumn = {
    key: "residencePlace",
    title: "Residence",
    render: (t, r) => r.residence && r.residence.length > 0 && r.residence[0].place && <>
        <PlaceLink place={r.residence[0].place}/>
        <Minor>{r.residence[0].place.type.name}</Minor>
    </>,
    width: 200,
    ...ColumnSubstringLocalSearch(r => r.residence && r.residence.length && r.residence[0].place && r.residence[0].place.name)
};

const MentionYear: IndividualRecordColumn = {
    key: "mentionDate",
    title: "Mention",
    render: (t, r) => r.mention && r.mention.length > 0 && <>{formatYearRange(r.mention[0].date, EmptyPrefixWords)}</>,
    width: 100
};

const DeathDatePlaceColumn: IndividualRecordColumn = {
    key: "deathDate",
    title: "Death date",
    dataIndex: "death.date",
    render: (t, r) => <DeathDatePlaceCell record={r}/>,
    width: 100
};

const DeathAgeColumn: IndividualRecordColumn = {
    ...YearColumn,
    key: "deathAge",
    title: "Age",
    dataIndex: "death.givenage",
    render: (t, r) => r.death && r.death.givenage > 0 && <>{r.death.givenage}</>,
    sorter: (r1, r2) => sortAges(r1.death && r1.death.givenage, r2.death && r2.death.givenage),
};

const BurialDateColumn: IndividualRecordColumn = {
    key: "burialDate",
    title: "Burial date",
    dataIndex: "burial.date",
    render: (t, r) => r.burial && <>{formatDateRange(r.burial.date, "day")}</>,
    width: 100
};

const RecordSetColumn: IndividualRecordColumn = {
    key: "recordSet",
    title: "Record set",
    render: (t, r) => r.record.recordSet && <RecordSetLink recordSet={r.record.recordSet}/>,
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