import * as React from "react";
import {CSSProperties} from "react";
import {ExternalRecordReference, Record, RecordSet} from "../../../protobuf/generated/record_pb";
import {Card, Icon} from "antd";
import {recordSetHref} from "../RecordLinks";
import {RecordCards} from "../../../components/records/RecordCards";
import {PersonSearch} from "../../../components/search/PersonSearch";
import {RecordSearchHandler} from "../../../components/search/RecordSearchHandler";
import {RecordResults} from "./RecordResults";
import {AsyncData} from "../../../components/fetch/AsyncData";
import {RecordPaginationHandler} from "../../../components/records/RecordPaginationHandler";
import {Link} from "../../../components/style/Links";
import {isBirthOrBaptismRecord} from "../../../components/records/RecordType";

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
            title={<><b>{recordSet.longtitle}</b> <span className="unimportant">{recordSet.numrecords} records</span></>}>

            <PartOf
                parent={recordSet.parent}/>

            <References
                references={recordSet.externalreferenceList}/>

            <Description
                record={recordSet}/>

            <RecordCards
                shortTitle
                records={this.props.recordSetChildren.data}
                style={MarginBottom}/>

            <PersonSearch
                disabled
                {...this.props}
                showAge={!isBirthOrBaptismRecord(this.props.recordSet)}
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
        <Icon type="up-square"/> Part of <a href={recordSetHref(props.parent)}>{props.parent.longtitle}</a>
    </div>;
};

const References = (props: {references: ReadonlyArray<ExternalRecordReference.AsObject>}) => {
    const references = props.references;
    if (!references || !references.length) return null;
    return <div className="references" style={MarginBottom}>
        <Icon type="book"/> This record is held at
        {references.map(ref => <> <Reference reference={ref}/></>)}
    </div>;
};

const Reference = (props: {reference: ExternalRecordReference.AsObject}) => {
    const reference = props.reference;
    if (!reference) return null;
    const c = <>{reference.archive} ({reference.reference})</>;
    return <>
        {reference.link ? <Link link={reference.link}>{c}</Link> : c}
    </>;
};

const MarginBottom: CSSProperties = {marginBottom: 16};