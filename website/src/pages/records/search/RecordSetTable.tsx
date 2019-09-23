import * as React from "react";
import {AsyncData} from "../../../components/fetch/AsyncData";
import {RecordSet} from "../../../protobuf/generated/record_pb";
import {Table} from "antd";
import {ErrorMessage} from "../../../components/style/Error";
import {ColumnProps} from "antd/es/table";
import {EmptyPlaceWords, FormattedYearRange} from "../../../components/date/FormattedDateRange";
import {recordSetHref} from "../RecordLinks";
import {HasClass} from "../../../components/style/HasClass";
import {recordTypeFromValue, recordTypeName} from "../../../components/records/RecordType";

type Props = {
    recordSets: AsyncData<ReadonlyArray<RecordSet.AsObject>>
}

export default class RecordSetTable extends React.PureComponent<Props> {

    render() {

        if (!this.props.recordSets.query) return <NoRecordsYet/>;
        if (this.props.recordSets.error) return <ErrorMessage message={"Error loading records: " + this.props.recordSets.error}/>;

        const data: RecordSet.AsObject[] = this.props.recordSets.data ? [].concat(this.props.recordSets.data) : [];

        return <Table
            className="bordered"
            style={{tableLayout: "fixed"}}
            loading={this.props.recordSets.loading}
            dataSource={data}
            columns={Columns}/>;

    }

}

const NoRecordsYet = (props: HasClass) => <div {...props}>No records loaded yet.</div>;

const Columns: ColumnProps<RecordSet.AsObject>[] = [
    {
        key: "name",
        title: "Name",
        dataIndex: "longtitle",
        render: (t, r) => <>
            <a href={recordSetHref(r)}>{t}</a>
            <div className="small unimportant">{recordTypeName(r)}</div>
        </>,
        width: 350,
        defaultSortOrder: "ascend",
        sorter: (a, b) => a.longtitle.localeCompare(b.longtitle)
    },
    {
        key: "numRecords",
        title: "Records",
        render: (t, r) => r.numrecords.toLocaleString(),
        sorter: (a, b) => a.numrecords - b.numrecords,
        width: 100
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
