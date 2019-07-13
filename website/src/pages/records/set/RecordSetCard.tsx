import * as React from "react";
import {CSSProperties} from "react";
import {Record, RecordSet} from "../../../protobuf/generated/record_pb";
import {Card} from "antd";
import {recordSetHref} from "../RecordLinks";
import {RecordCards} from "../../../components/records/RecordCards";
import {NameAgeSearch} from "../../../components/search/NameAgeSearch";
import {RecordSearchHandler} from "../../../components/search/RecordSearchHandler";
import {RecordResults} from "./RecordResults";
import {AsyncData} from "../../../components/fetch/AsyncData";
import {RecordPaginationHandler} from "../../../components/records/RecordPaginationHandler";

type Props = RecordPaginationHandler & RecordSearchHandler & {
    recordSet: Readonly<RecordSet.AsObject>
    recordSetChildren: AsyncData<ReadonlyArray<RecordSet.AsObject>>;
    records: AsyncData<ReadonlyArray<Record.AsObject>>
    searchResults: AsyncData<ReadonlyArray<Record.AsObject>>;
}

export default class RecordSetCard extends React.PureComponent<Props> {

    render() {

        const recordSet = this.props.recordSet;

        return <Card
            className="info"
            title={<b>{recordSet.longtitle}</b>}>

            <PartOf
                parent={recordSet.parent}/>

            <Description
                record={recordSet}/>

            <RecordCards
                shortTitle
                records={this.props.recordSetChildren.data}
                style={MarginBottom}/>

            <NameAgeSearch
                disabled
                {...this.props}
                style={MarginBottom}/>

            <RecordResults
                {...this.props}
                records={this.props.records}/>

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