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
import {RouteComponentProps} from "react-router";
import {Loading} from "../../../components/style/Loading";

type Props = RecordPaginationHandler & RecordSearchHandler & RouteComponentProps<any> & {
    loading: boolean;
    recordSet: Readonly<RecordSet.AsObject>
    recordSetChildren: AsyncData<ReadonlyArray<RecordSet.AsObject>>;
    records: AsyncData<ReadonlyArray<Record.AsObject>>
    searchResults: AsyncData<ReadonlyArray<Record.AsObject>>;
}

export default class RecordSetCard extends React.PureComponent<Props> {

    constructor(props) {
        super(props);
    }


    render() {

        const recordSet = this.props.recordSet;

        return <Card
            className="info"
            title={recordSet ? <><b>{recordSet.longtitle}</b> <span className="unimportant">{recordSet.numrecords} records</span></> : <Loading/>}>

            {recordSet && <>
                <PartOf
                    parent={recordSet.parent}/>

                <References
                    references={recordSet.externalreferenceList.length ? recordSet.externalreferenceList : recordSet.parent && recordSet.parent.externalreferenceList}/>

                <Description
                    record={recordSet}/>
            </>}

            <RecordCards
                shortTitle
                records={this.props.recordSetChildren.data}
                style={MarginBottom}/>

            <PersonSearch
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
        <Icon type="up-square"/> Part of <a href={recordSetHref(props.parent)}>{props.parent.longtitle}</a>
    </div>;
};

const References = (props: {references: ReadonlyArray<ExternalRecordReference.AsObject>}) => {
    const references = props.references;
    if (!references || !references.length) return null;
    return <div className="references" style={MarginBottom}>
        <Icon type="book"/> These records are held at
        {references.map(ref => <> <Reference reference={ref}/></>)}
    </div>;
};

const Reference = (props: {reference: ExternalRecordReference.AsObject}) => {
    const reference = props.reference;
    if (!reference) return null;
    const item = <>(reference {reference.reference})</>;
    return <>
        <Link link={reference.archive.website} iconPath={reference.archive.icon} newWindow>{reference.archive.name}</Link>
        {" "}
        {reference.link ? <Link link={reference.link} newWindow>{item}</Link> : item}
    </>;
};

const MarginBottom: CSSProperties = {marginBottom: 16};