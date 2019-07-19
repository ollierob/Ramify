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
import {RouteComponentProps} from "react-router";
import {Loading} from "../../../components/style/Loading";
import {stringMultimap} from "../../../components/Maps";
import {RecordSetReferences} from "../../../components/records/RecordSetReference";
import {joinComponents} from "../../../components/Components";

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
            title={recordSet ? <>Records of <b>{recordSet.longtitle}</b> <span className="unimportant">{recordSet.numrecords} records</span></> : <Loading/>}>

            {recordSet && <>
                <PartOf
                    parent={recordSet.parent}/>

                <References
                    references={recordSet.externalreferenceList.length ? recordSet.externalreferenceList : recordSet.parent && recordSet.parent.externalreferenceList}/>

                <Description
                    record={recordSet}/>
            </>}

            <RecordCards
                {...this.props}
                fixedWidth={this.useFixedWidthCards()}
                shortTitle
                records={this.props.recordSetChildren.data}
                style={MarginBottom}/>

            <PersonSearch
                {...this.props}
                disabled
                style={MarginBottom}/>

            <RecordResults
                {...this.props}
                records={this.props.records}/>

        </Card>;

    }

    private useFixedWidthCards(): boolean {
        const children = this.props.recordSetChildren.data;
        if (!children || !children.length) return false;
        return children.every(c => (c.shorttitle || c.longtitle).length < 32);
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
        <Icon type="up-square"/> Part of the records of <a href={recordSetHref(props.parent)}>{props.parent.longtitle}</a>
    </div>;
};

const References = (props: {references: ReadonlyArray<ExternalRecordReference.AsObject>}) => {
    const references = props.references;
    if (!references || !references.length) return null;
    const archiveGroupings = stringMultimap(references, ref => ref.archive.id);
    return <div className="references" style={MarginBottom}>
        <Icon type="book"/> These records are held
        {" "}
        {joinComponents(Object.values(archiveGroupings).map(refs => <>
            {refs[0].archive.pb_private ? "in a private collection" : <>at <RecordSetReferences archive={refs[0].archive} references={refs}/></>}
        </>), " and ")}
    </div>;
};

const MarginBottom: CSSProperties = {marginBottom: 16};