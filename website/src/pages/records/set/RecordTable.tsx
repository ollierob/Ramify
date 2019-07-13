import * as React from "react";
import {Record} from "../../../protobuf/generated/record_pb";
import {Table} from "antd";
import {RecordPaginationHandler} from "../../../components/records/RecordPaginationHandler";
import {ColumnProps} from "antd/es/table";
import {Person} from "../../../protobuf/generated/person_pb";
import {RecordType} from "../../../components/records/RecordType";
import {nameToString} from "../../../components/people/Name";
import {Event} from "../../../protobuf/generated/event_pb";

type Props = Partial<RecordPaginationHandler> & {
    type: RecordType;
    loading: boolean;
    records: ReadonlyArray<Record.AsObject>
}

type State = {
    data: IndividualRecord[];
    columns: ColumnProps<IndividualRecord>[]
}

type IndividualRecord = {
    person: Person.AsObject;
    birth?: Event.AsObject;
}

export class RecordTable extends React.PureComponent<Props, State> {

    constructor(props) {
        super(props);
        this.state = {
            data: buildIndividualRecords(props.records),
            columns: determineColumns(this.props.type)
        };
    }


    render() {

        const data = this.state.data;
        if (!data || !data.length) return <>No records found.</>;

        return <>

            <Table
                loading={this.props.loading}
                dataSource={data}
                columns={this.state.columns}/>

        </>;

    }

    componentDidUpdate(prevProps: Readonly<Props>) {
        if (this.props.type != prevProps.type)
            this.setState({columns: determineColumns(this.props.type)});
        if (this.props.records != prevProps.records)
            this.setState({data: buildIndividualRecords(this.props.records)});
    }

}

function buildIndividualRecords(records: ReadonlyArray<Record.AsObject>): IndividualRecord[] {
    if (!records || !records.length) return [];
    const out: IndividualRecord[] = [];
    records.forEach(record => {
        record.familyList.forEach(family => {
            family.personList.forEach(person => {
                const record: IndividualRecord = {person: person};
                out.push(record);
            });
        });
    });
    return out;
}

function determineColumns(type: RecordType): ColumnProps<IndividualRecord>[] {
    const columns = [NameColumn];
    return columns;
}

const NameColumn: ColumnProps<IndividualRecord> = {
    key: "name",
    title: "Name",
    dataIndex: "person.name.surname",
    render: (t, r) => nameToString(r.person.name)
};