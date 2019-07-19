import * as React from "react";
import {ExternalRecordReference} from "../../protobuf/generated/record_pb";
import {Archive} from "../../protobuf/generated/archive_pb";
import {Icon, Popover} from "antd";
import {RecordsIcon} from "../images/Icons";

export const RecordSetReferences = (props: {archive: Archive.AsObject, references: ReadonlyArray<ExternalRecordReference.AsObject>}) => {
    const archive = props.archive;
    if (!archive) return null;
    return <Popover content={<PopoverContent {...props}/>} placement="bottomLeft">
        <a>
            {archive.icon && <><img src={archive.icon} className="image" alt={archive.name}/> </>} {archive.name}
        </a>
    </Popover>;
};

const PopoverContent = (props: {archive: Archive.AsObject, references: ReadonlyArray<ExternalRecordReference.AsObject>}) => {
    const references = props.references;
    if (!references || !references.length) return null;
    const archive = props.archive;
    return <>
        <div>
            <a href={archive.website.href} target="_blank">
                <Icon type="export"/> Archive website
            </a>
        </div>
        <div>
            <RecordsIcon/> References:
        </div>
        <ul>
            {references.map(r => <li>
                {r.reference}
            </li>)}
        </ul>
    </>;
};

