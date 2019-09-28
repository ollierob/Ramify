import * as React from "react";
import {CSSProperties} from "react";
import {ExternalRecordReference, RecordSet, RecordSetRelatives} from "../../../protobuf/generated/record_pb";
import {Card, Icon} from "antd";
import {recordSetHref} from "../RecordLinks";
import {RecordCards} from "../../../components/records/RecordCards";
import {RecordSearchHandler} from "../../../components/search/RecordSearchHandler";
import {RecordResults} from "./RecordResults";
import {AsyncData} from "../../../components/fetch/AsyncData";
import {RecordPaginationHandler} from "../../../components/records/RecordPaginationHandler";
import {RouteComponentProps} from "react-router";
import {Loading} from "../../../components/style/Loading";
import {stringMultimap} from "../../../components/Maps";
import {RecordSetReferences} from "../../../components/records/RecordSetReference";
import {joinComponents} from "../../../components/Components";
import {EnrichedRecord} from "../../../components/records/RecordLoader";
import {PlaceBundle} from "../../../protobuf/generated/place_pb";
import {PlaceContextMenu} from "../../../components/places/PlaceContextMenu";

type Props = RecordPaginationHandler & RecordSearchHandler & RouteComponentProps<any> & {
    loading: boolean;
    recordSet: Readonly<RecordSet.AsObject>
    relatives: AsyncData<RecordSetRelatives.AsObject>;
    records: AsyncData<ReadonlyArray<EnrichedRecord>>
    searchResults: AsyncData<ReadonlyArray<EnrichedRecord>>;
    creatorPlace: AsyncData<Readonly<PlaceBundle.AsObject>>
}

export default class RecordSetCard extends React.PureComponent<Props> {

    render() {

        const recordSet = this.props.recordSet;
        const relatives: RecordSetRelatives.AsObject = this.props.relatives.data || {childList: [], nextList: []};

        return <Card
            className="info"
            title={recordSet ? <>Records of <b>{recordSet.longtitle}</b> <span className="unimportant">{recordSet.numrecords} records</span></> : <Loading/>}>

            {recordSet && <>

                <Creator
                    place={this.props.creatorPlace}/>

                <References
                    references={recordSet.externalreferenceList.length ? recordSet.externalreferenceList : relatives.parent && relatives.parent.externalreferenceList}/>

                <Description
                    record={recordSet}/>

            </>}

            <RecordCards
                {...this.props}
                fixedWidth={this.useFixedWidthCards()}
                shortTitle
                records={relatives.childList}
                style={MarginBottom}/>

            <RecordResults
                {...this.props}
                records={this.props.records}
                showRecordSet={relatives.childList.length > 0}/>

        </Card>;

    }

    private useFixedWidthCards(): boolean {
        const relatives = this.props.relatives.data;
        if (!relatives || !relatives.childList.length) return false;
        return relatives.childList.every(c => (c.shorttitle || c.longtitle).length < 32);
    }

}

const Description = (props: {record: RecordSet.AsObject}) => {
    const description = props.record.description;
    if (!description) return null;
    return <div className="description" style={MarginBottom}>
        {description}
    </div>;
};

const Creator = (props: {place: AsyncData<Readonly<PlaceBundle.AsObject>>}) => {
    const place = props.place;
    if (!place.data || !place.loading) return null;
    return <div className="relationship place" style={MarginBottom}>
        <Icon type="edit"/> These records were created by <PlaceContextMenu place={place.data && place.data.place} loading={place.loading} showType/>
    </div>;
};

const NextRelatives = (props: {next: ReadonlyArray<RecordSet.AsObject>}) => {
    const next = props.next;
    if (!next || !next.length) return null;
    return <div className="relationship next" style={MarginBottom}>
        <Icon type="right-square"/>
        {" "}
        Next in series {next.length > 1 ? "are" : "is"}
        {" "}
        {joinComponents(next.map(n => <a href={recordSetHref(n)}>{n.longtitle}</a>), " and ")}
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