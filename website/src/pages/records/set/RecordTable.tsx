import * as React from "react";
import {Record, RecordSet} from "../../../protobuf/generated/record_pb";
import {Table} from "antd";
import {RecordPaginationHandler} from "../../../components/records/RecordPaginationHandler";
import {Person} from "../../../protobuf/generated/person_pb";
import {Event} from "../../../protobuf/generated/event_pb";
import {isBirthEvent, isBurialEvent, isDeathEvent, isResidenceEvent} from "../../../components/event/Event";
import {determineColumns, RecordColumn} from "./RecordTableColumns";
import {NoData} from "../../../components/style/NoData";

type Props = Partial<RecordPaginationHandler> & {
    recordSet: RecordSet.AsObject;
    loading: boolean;
    records: ReadonlyArray<Record.AsObject>
    showRecordSet?: boolean;
}

type State = {
    data: IndividualRecord[];
    columns: RecordColumn[]
}

export type IndividualRecord = {
    person: Person.AsObject;
    birth?: Event.AsObject;
    residence?: Event.AsObject;
    death?: Event.AsObject;
    burial?: Event.AsObject;
    image?: string;
}

export class RecordTable extends React.PureComponent<Props, State> {

    constructor(props) {
        super(props);
        const properties = {};
        const data = buildIndividualRecords(props.references, properties);
        const columns = determineColumns(props.recordSet, properties, props.showRecordSet);
        this.state = {data, columns};
    }


    render() {

        const data = this.state.data;
        if (!data || !data.length) return <NoData/>;

        return <Table
            className="table"
            loading={this.props.loading}
            dataSource={data}
            columns={this.state.columns}/>;

    }

    componentDidUpdate(prevProps: Readonly<Props>) {
        if (this.props.records != prevProps.records) {
            const properties = {};
            const data = buildIndividualRecords(this.props.records, properties);
            const columns = determineColumns(this.props.recordSet, properties, this.props.showRecordSet);
            this.setState({data, columns});
        }
    }

}

export type RecordProperties = {
    hasBirth?: boolean;
    hasResidence?: boolean;
    hasDeath?: boolean;
    hasBurial?: boolean;
}

function buildIndividualRecords(records: ReadonlyArray<Record.AsObject>, properties: RecordProperties): IndividualRecord[] {
    if (!records || !records.length) return [];
    const out: IndividualRecord[] = [];
    records.forEach(record => {
        record.familyList.forEach(family => {
            family.personList.forEach(person => out.push(createRecord(person)));
        });
    });
    properties.hasBirth = out.some(r => r.birth);
    properties.hasResidence = out.some(r => r.residence);
    properties.hasDeath = out.some(r => r.death);
    properties.hasBurial = out.some(r => r.burial);
    return out;
}

function createRecord(person: Person.AsObject): IndividualRecord {
    //TODO include record set
    const out: IndividualRecord = {person};
    out.birth = person.eventsList.find(isBirthEvent);
    out.residence = person.eventsList.find(isResidenceEvent);
    out.death = person.eventsList.find(isDeathEvent);
    out.burial = person.eventsList.find(isBurialEvent);
    return out;
}
