import * as React from "react";
import {RecordSet} from "../../../protobuf/generated/record_pb";
import {Card} from "antd";
import {recordSetHref} from "../RecordLinks";
import {RecordCards} from "../../../components/records/RecordCards";
import {NameSearch} from "../../../components/search/NameSearch";
import {CSSProperties} from "react";

type Props = {
    recordSet: Readonly<RecordSet.AsObject>
    recordSetChildren: ReadonlyArray<RecordSet.AsObject>
}

export default class RecordSetCard extends React.PureComponent<Props> {

    render() {

        const recordSet = this.props.recordSet;
        const children = this.props.recordSetChildren || [];

        return <Card
            className="info"
            title={<b>{recordSet.longtitle}</b>}>

            <PartOf parent={recordSet.parent}/>

            <Description record={recordSet}/>

            <NameSearch
                disabled
                searching={false}
                doSearch={null}
                style={MarginBottom}/>

            <RecordCards
                shortTitle
                records={children}/>

        </Card>;

    }

}

const Description = (props: {record: RecordSet.AsObject}) => {
    const description = props.record.description;
    if (!description) return null;
    return <div className="description" style={MarginBottom}>
        {description}
    </div>;
};

const PartOf = (props: {parent: RecordSet.AsObject}) => {
    if (!props.parent) return null;
    return <div className="parent" style={MarginBottom}>
        Part of <a href={recordSetHref(props.parent)}>{props.parent.longtitle}</a>
    </div>;
};

const MarginBottom: CSSProperties = {marginBottom: 16};