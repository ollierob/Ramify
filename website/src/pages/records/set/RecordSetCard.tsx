import * as React from "react";
import {RecordSet} from "../../../protobuf/generated/record_pb";
import {Card} from "antd";

type Props = {
    recordSet: Readonly<RecordSet.AsObject>
}

export default class RecordSetCard extends React.PureComponent<Props> {

    render() {

        const recordSet = this.props.recordSet;

        return <Card
            className="info"
            title={<b>{recordSet.title}</b>}>

            <div className="description">
                {recordSet.description}
            </div>

        </Card>;

    }

}