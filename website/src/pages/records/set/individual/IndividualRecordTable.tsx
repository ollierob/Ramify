import * as React from "react";
import {RecordSet} from "../../../../protobuf/generated/record_pb";
import {Table} from "antd";
import {RecordPaginationHandler} from "../../../../components/records/RecordPaginationHandler";
import {isBaptismEvent, isBirthEvent, isBurialEvent, isDeathEvent, isMentionEvent, isResidenceEvent} from "../../../../components/event/Event";
import {NoData} from "../../../../components/style/NoData";
import {DEFAULT_RECORD_LOADER, EnrichedIndividualRecord} from "../../../../components/records/RecordLoader";
import {IndividualRecord, IndividualRecordProperties} from "./IndividualRecord";
import {determineColumns, IndividualRecordColumn} from "./IndividualRecordTableColumns";
import {AsyncData, asyncLoadData, mapAsyncData} from "../../../../components/fetch/AsyncData";
import {RecordSetId} from "../../../../components/records/RecordSet";

type Props = Partial<RecordPaginationHandler> & {
    recordSet: RecordSet.AsObject;
    showRecordSet?: boolean;
}

type State = {
    columns: IndividualRecordColumn[];
    records: AsyncData<IndividualRecord[]>;
}

export default class IndividualRecordTable extends React.PureComponent<Props, State> {

    private readonly recordLoader = DEFAULT_RECORD_LOADER;

    constructor(props: Props) {
        super(props);
        this.state = {
            columns: [],
            records: {loading: true}
        };
    }

    render() {

        const data = this.state.records.data || [];
        const loading = this.state.records.loading;
        if (!loading && !data.length) return <NoData/>;

        return <Table
            className="table"
            loading={loading}
            dataSource={data}
            columns={this.state.columns}/>;

    }

    componentDidMount() {
        if (this.props.recordSet)
            this.loadRecords(this.props.recordSet.id);
    }

    componentDidUpdate(prevProps: Readonly<Props>, prevState: Readonly<State>) {
        if (this.props.recordSet && (!prevProps.recordSet || this.props.recordSet.id != prevProps.recordSet.id))
            this.loadRecords(this.props.recordSet.id);
        if (this.state.records.data && this.state.records.data != prevState.records.data)
            this.determineColumns();
    }

    private loadRecords(id: RecordSetId) {
        if (!id) return;
        asyncLoadData(
            id,
            id => this.recordLoader.loadIndividualRecords(id, {children: true, limit: 100}),
            records => this.setState({records: mapAsyncData(records, buildIndividualRecords)}));
    }

    private determineColumns() {
        this.setState(current => {
            const properties = determineProperties(current.records.data);
            return {columns: determineColumns(this.props.recordSet, properties, this.props.showRecordSet)};
        });
    }

}

function buildIndividualRecords(records: ReadonlyArray<EnrichedIndividualRecord>): IndividualRecord[] {
    if (!records || !records.length) return [];
    return records.map(createRecord);
}

function determineProperties(records: ReadonlyArray<IndividualRecord>): IndividualRecordProperties {
    const properties: IndividualRecordProperties = {};
    properties.hasBirth = records.some(r => r.birth);
    properties.hasBaptism = records.some(r => r.baptism);
    properties.hasResidence = records.some(r => r.residence && r.residence.length > 0);
    properties.hasMention = records.some(r => r.mention && r.mention.length > 0);
    properties.hasDeath = records.some(r => r.death);
    properties.hasBurial = records.some(r => r.burial);
    return properties;
}

function createRecord(record: EnrichedIndividualRecord): IndividualRecord {
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
