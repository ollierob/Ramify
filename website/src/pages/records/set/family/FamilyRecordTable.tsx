import * as React from "react";
import {RecordPaginationHandler} from "../../../../components/records/RecordPaginationHandler";
import {RecordSet} from "../../../../protobuf/generated/record_pb";
import {Table} from "antd";
import {AsyncData, asyncLoadData, mapAsyncData} from "../../../../components/fetch/AsyncData";
import {buildFamilyRecords, FamilyRecord} from "./FamilyRecord";
import {NoData} from "../../../../components/style/NoData";
import {RecordSetId} from "../../../../components/records/RecordSet";
import {DEFAULT_RECORD_LOADER} from "../../../../components/records/RecordLoader";
import {ErrorMessage} from "../../../../components/style/Error";
import {determineColumns, FamilyRecordColumn} from "./FamilyRecordTableColumns";
import "./FamilyRecordTable.css";

type Props = Partial<RecordPaginationHandler> & {
    recordSet: RecordSet.AsObject;
    showRecordSet?: boolean;
}

type State = {
    columns: FamilyRecordColumn[];
    records: AsyncData<FamilyRecord[]>
}

export default class FamilyRecordTable extends React.PureComponent<Props, State> {

    private readonly recordLoader = DEFAULT_RECORD_LOADER;

    constructor(props) {
        super(props);
        this.state = {
            records: {loading: true},
            columns: []
        };
    }

    render() {

        if (this.state.records.error) return <ErrorMessage message={this.state.records.error}/>;

        const data: FamilyRecord[] = this.state.records.data || [];
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
        if (this.props.recordSet && this.props.recordSet != prevProps.recordSet)
            this.loadRecords(this.props.recordSet.id);
        if (this.state.records.data && this.state.records.data != prevState.records.data)
            this.setColumns(this.state.records.data);
    }

    private loadRecords(id: RecordSetId) {
        if (!id) return;
        asyncLoadData(
            id,
            id => this.recordLoader.loadFamilyRecords(id, {children: true, limit: 100}),
            records => this.setState({records: mapAsyncData(records, buildFamilyRecords)}));
    }

    private setColumns(records: ReadonlyArray<FamilyRecord>) {
        const columns = determineColumns(this.props.recordSet, records);
        this.setState({columns});
    }

}