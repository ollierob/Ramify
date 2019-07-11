import * as React from "react";
import {AsyncData} from "../../../components/fetch/AsyncData";
import {RecordSet} from "../../../protobuf/generated/record_pb";
import {Loading} from "../../../components/Loading";

type Props = {
    recordSets: AsyncData<ReadonlyArray<RecordSet.AsObject>>
}

export default class RecordSetTable extends React.PureComponent<Props> {

    render() {

        if (!this.props.recordSets.query) return <NoRecordsYet/>;
        if (this.props.recordSets.loading) return <Loading/>;

        return null;

    }

}

const NoRecordsYet = (props: {}) => <>No records loaded yet.</>;
