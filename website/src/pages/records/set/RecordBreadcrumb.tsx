import * as React from "react";
import {SubMenu} from "../../SubMenu";
import {RecordSetHierarchy, RecordSetRelatives} from "../../../protobuf/generated/record_pb";
import {recordSetHref} from "../RecordLinks";
import {Popover} from "antd";
import {NextIcon, PrevIcon, RecordsIcon} from "../../../components/images/Icons";

export const RecordBreadcrumb = (props: {hierarchy: RecordSetHierarchy.AsObject}) => {
    return <SubMenu>
        {props.hierarchy && <Hierarchy {...props}/>}
    </SubMenu>;
};

const Hierarchy = (props: {hierarchy: RecordSetHierarchy.AsObject}) => {
    let list = toList(props.hierarchy);
    return <div className="records">
        <RecordsIcon style={{marginRight: 8}}/>
        {list.map((r, i) => <Breadcrumb relatives={r} first={i == 0} last={i == list.length - 1}/>)}
    </div>;
};

const Breadcrumb = (props: {relatives: RecordSetRelatives.AsObject, first?: boolean, last?: boolean}) => {
    const self = props.relatives.self;
    return <>
        <Popover content={<BreadcrumbDropdown {...props}/>} placement="bottomLeft">
            <span className="record">
                <a href={recordSetHref(self)}>
                    <b>
                        {props.last ? self.longtitle : (self.shorttitle || self.longtitle)}
                    </b>
                </a>
            </span>
        </Popover>
        {!props.last && " » "}
    </>;
};

const BreadcrumbDropdown = (props: {relatives: RecordSetRelatives.AsObject}) => {
    const relatives = props.relatives;
    if (!relatives) return null;
    return <>
        {relatives.previous && <div>
            <PrevIcon/> Previous:
            {" "}
            <a href={recordSetHref(relatives.previous)}>{relatives.previous.longtitle}</a>
        </div>}
        {relatives.nextList && relatives.nextList.length > 0 && <div>
            <NextIcon/> Next:
            {" "}
            <a href={recordSetHref(relatives.nextList[0])}>{relatives.nextList[0].longtitle}</a>
        </div>}
    </>;
};

function toList(hierarchy: RecordSetHierarchy.AsObject): ReadonlyArray<RecordSetRelatives.AsObject> {
    if (!hierarchy) return [];
    const array: RecordSetRelatives.AsObject[] = [];
    do {
        array.push(hierarchy.self);
        hierarchy = hierarchy.parent;
    } while (hierarchy != null);
    return array.reverse();
}