import * as React from "react";
import {RecordSet} from "../../../protobuf/generated/record_pb";
import {Table} from "antd";
import {RecordPaginationHandler} from "../../../components/records/RecordPaginationHandler";
import {isBaptismEvent, isBirthEvent, isBurialEvent, isDeathEvent, isMentionEvent, isResidenceEvent} from "../../../components/event/Event";
import {NoData} from "../../../components/style/NoData";
import {EnrichedRecord} from "../../../components/records/RecordLoader";
import {IndividualRecord} from "./IndividualRecord";
import {determineColumns, IndividualRecordColumn} from "./IndividualRecordTableColumns";

type Props = Partial<RecordPaginationHandler> & {
    recordSet: RecordSet.AsObject;
    loading: boolean;
    records: ReadonlyArray<EnrichedRecord>
    showRecordSet?: boolean;
}

type State = {
    data: IndividualRecord[];
    columns: IndividualRecordColumn[]
}

export class IndividualRecordTable extends React.PureComponent<Props, State> {

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
    hasBaptism?: boolean;
    hasResidence?: boolean;
    hasMention?: boolean;
    hasDeath?: boolean;
    hasBurial?: boolean;
}

function buildIndividualRecords(records: ReadonlyArray<EnrichedRecord>, properties: RecordProperties): IndividualRecord[] {
    if (!records || !records.length) return [];
    const out: IndividualRecord[] = [];
    records.forEach(record => out.push(createRecord(record)));
    properties.hasBirth = out.some(r => r.birth);
    properties.hasBaptism = out.some(r => r.baptism);
    properties.hasResidence = out.some(r => r.residence && r.residence.length > 0);
    properties.hasMention = out.some(r => r.mention && r.mention.length > 0);
    properties.hasDeath = out.some(r => r.death);
    properties.hasBurial = out.some(r => r.burial);
    return out;
}

function createRecord(record: EnrichedRecord): IndividualRecord {
    //TODO include resolved record set
    const person = record.person;
    const out: IndividualRecord = {person, record};
    out.birth = person.eventsList.find(isBirthEvent);
    out.baptism = person.eventsList.find(isBaptismEvent);
    out.residence = person.eventsList.filter(isResidenceEvent);
    out.mention = person.eventsList.filter(isMentionEvent);
    out.death = person.eventsList.find(isDeathEvent);
    out.burial = person.eventsList.find(isBurialEvent);
    return out;
}
