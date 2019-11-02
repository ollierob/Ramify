import * as React from "react";
import {ExternalRecordReference} from "../../protobuf/generated/record_pb";
import {Archive} from "../../protobuf/generated/archive_pb";
import {Icon, Popover} from "antd";
import {RecordsIcon} from "../images/Icons";
import {CSSProperties} from "react";

export const RecordSetReferences = (props: {archive: Archive.AsObject, references: ReadonlyArray<ExternalRecordReference.AsObject>}) => {
    const archive = props.archive;
    if (!archive) return null;
    if (archive.pb_private) return <>Private collection</>;
    return <Popover content={<PopoverContent {...props}/>} placement="bottomLeft">
        <a>
            {archiveIcon(archive)}
            {" "}
            {archive.name}
        </a>
    </Popover>;
};

function archiveIcon(archive: Archive.AsObject) {
    if (archive.icon) return <img src={archive.icon} className="image" alt={archive.name}/>;
    return <Icon type="link" className="image" title={archive.name}/>;
}

const PopoverContent = (props: {archive: Archive.AsObject, references: ReadonlyArray<ExternalRecordReference.AsObject>}) => {
    const references = props.references;
    if (!references || !references.length) return null;
    const archive = props.archive;
    return <div className="recordSetReferences">
        {archive.website && <div style={MarginBottom}>
            <a href={archive.website.href} target="_blank">
                <Icon type="export"/> Archive website
            </a>
        </div>}
        <div>
            <RecordsIcon/> References:
        </div>
        <ul>
            {references.map(r => <li>
                {r.link ? <a href={r.link.href} target="_blank">{r.reference}</a> : r.reference}
            </li>)}
        </ul>
    </div>;
};

const MarginBottom: CSSProperties = {marginBottom: 4};

