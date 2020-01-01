import * as React from "react";
import {ExternalRecordReference, RecordSet, RecordSetRelatives} from "../../../protobuf/generated/record_pb";
import {Card, Icon} from "antd";
import {RecordBrowser} from "./RecordBrowser";
import {AsyncData} from "../../../components/fetch/AsyncData";
import {RecordPaginationHandler} from "../../../components/records/RecordPaginationHandler";
import {RouteComponentProps} from "react-router";
import {Loading} from "../../../components/style/Loading";
import {stringMultimap} from "../../../components/Maps";
import {RecordSetReferences} from "../../../components/records/RecordSetReference";
import {joinComponents} from "../../../components/Components";
import {PlaceBundle} from "../../../protobuf/generated/place_pb";
import {PlaceLink} from "../../../components/places/PlaceLink";
import {RecordSetChildCollapseCards} from "../../../components/records/RecordSetChildCollapseCards";
import "./RecordSetCard.css";

type Props = RecordPaginationHandler & RouteComponentProps<any> & {
    loading: boolean;
    recordSet: Readonly<RecordSet.AsObject>
    relatives: AsyncData<RecordSetRelatives.AsObject>;
    creatorPlace: AsyncData<Readonly<PlaceBundle.AsObject>>
}

export default class RecordSetCard extends React.PureComponent<Props> {

    render() {

        const recordSet = this.props.recordSet;
        const relatives: RecordSetRelatives.AsObject = this.props.relatives.data || {childList: [], nextList: []};
        const hasChildren = relatives.childList.length > 0;

        return <Card
            className="large info"
            title={<Title recordSet={recordSet}/>}>

            {recordSet && <>

                <Creator
                    place={this.props.creatorPlace}/>

                <References
                    references={recordSet.externalreferenceList.length ? recordSet.externalreferenceList : relatives.parent && relatives.parent.externalreferenceList}/>

                <Description
                    record={recordSet}/>

            </>}

            <RecordSetChildCollapseCards
                {...this.props}
                fixedWidth={this.useFixedWidthCards()}
                shortTitle
                records={relatives.childList}
                ignoreNone/>

            <RecordBrowser
                {...this.props}
                hasChildren={hasChildren}/>

        </Card>;

    }

    private useFixedWidthCards(): boolean {
        const relatives = this.props.relatives.data;
        if (!relatives || !relatives.childList.length) return false;
        return relatives.childList.every(c => (c.shorttitle || c.longtitle).length < 32);
    }

}

const Title = (props: {recordSet: RecordSet.AsObject}) => {
    const recordSet = props.recordSet;
    if (!recordSet) return <Loading/>;
    return <>
        {prefixWithRecords(recordSet.longtitle) && <>Records of </>}
        <b>{recordSet.longtitle}</b>
        {" "}
        <span className="unimportant">
            {recordSet.numrecords.toLocaleString()} records
            {recordSet.numrecords > 0 && <>, {recordSet.numindividuals.toLocaleString()} individuals</>}
        </span></>;
};

function prefixWithRecords(title: string) {
    return !title.endsWith("Registers")
        && !title.endsWith("Returns");
}

const Description = (props: {record: RecordSet.AsObject}) => {
    const description = props.record.description;
    if (!description) return null;
    return <div className="description">
        {description}
    </div>;
};

const Creator = (props: {place: AsyncData<Readonly<PlaceBundle.AsObject>>}) => {
    const place = props.place;
    if (!place.data && !place.loading) return null;
    return <div className="relationship place">
        <Icon type="edit"/> These records were created by <PlaceLink place={place.data && place.data.place} loading={place.loading} showType/>
    </div>;
};

const References = (props: {references: ReadonlyArray<ExternalRecordReference.AsObject>}) => {
    const references = props.references;
    if (!references || !references.length) return null;
    const archiveGroupings = stringMultimap(references, ref => ref.archive.id);
    return <div className="references">
        <Icon type="book"/> These records are held
        {" "}
        {joinComponents(Object.values(archiveGroupings).map(refs => <>
            {refs[0].archive.pb_private ? "in a private collection" : <>at <RecordSetReferences archive={refs[0].archive} references={refs}/></>}
        </>), " and ")}
    </div>;
};