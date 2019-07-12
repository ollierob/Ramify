import * as React from "react";
import {AsyncData} from "../../../components/fetch/AsyncData";
import {RecordSet} from "../../../protobuf/generated/record_pb";
import {Loading} from "../../../components/Loading";
import {Table} from "antd";
import {ErrorMessage} from "../../../components/style/Error";
import {ColumnProps} from "antd/es/table";
import {EmptyPlaceWords, FormattedYearRange} from "../../../components/date/FormattedDateRange";

type Props = {
    recordSets: AsyncData<ReadonlyArray<RecordSet.AsObject>>
}

export default class RecordSetTable extends React.PureComponent<Props> {

    render() {

        if (!this.props.recordSets.query) return <NoRecordsYet/>;
        if (this.props.recordSets.error) return <ErrorMessage message={this.props.recordSets.error}/>

        const data: RecordSet.AsObject[] = this.props.recordSets.data ? [].concat(this.props.recordSets.data) : [];

        return <Table
            className="bordered"
            loading={this.props.recordSets.loading}
            dataSource={data}
            columns={Columns}/>;

    }

}

const NoRecordsYet = (props: {}) => <>No records loaded yet.</>;

const Columns: ColumnProps<RecordSet.AsObject>[] = [
    {
        key: "name",
        title: "Name",
        dataIndex: "title",
        width: 300,
        defaultSortOrder: "ascend",
        sorter: (a, b) => a.title.localeCompare(b.title)
    },
    {
        key: "date",
        title: "Date",
        render: (t, r) => <FormattedYearRange date={r.date} words={EmptyPlaceWords}/>,
        width: 120,
    },
    {
        key: "description",
        title: "Description",
        dataIndex: "description",
        sorter: true
    }
];
