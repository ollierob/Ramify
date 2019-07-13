import * as React from "react";
import {RecordSet} from "../../../protobuf/generated/record_pb";
import {Card} from "antd";
import {recordSetHref} from "../RecordLinks";

type Props = {
    recordSet: Readonly<RecordSet.AsObject>
}

export default class RecordSetCard extends React.PureComponent<Props> {

    render() {

        const recordSet = this.props.recordSet;

        return <Card
            className="info"
            title={<b>{recordSet.title}</b>}>

            <PartOf parent={recordSet.parent}/>

            <div className="description">
                {recordSet.description}
            </div>

        </Card>;

    }

}

const PartOf = (props: {parent: RecordSet.AsObject}) => {
    if (!props.parent) return null;
    return <div className="parent">
        Part of <a href={recordSetHref(props.parent)}>{props.parent.title}</a>
    </div>;
};