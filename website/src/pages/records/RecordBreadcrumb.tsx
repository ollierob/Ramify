import * as React from "react";
import {SubMenu} from "../SubMenu";
import {RecordSetHierarchy, RecordSetRelatives} from "../../protobuf/generated/record_pb";
import {recordSetHref} from "./RecordLinks";
import {recordTypeName} from "../../components/records/RecordType";

export const RecordBreadcrumb = (props: {hierarchy: RecordSetHierarchy.AsObject}) => {
    return <SubMenu>
        {props.hierarchy && <Hierarchy {...props}/>}
    </SubMenu>;
};

const Hierarchy = (props: {hierarchy: RecordSetHierarchy.AsObject}) => {
    let list = toList(props.hierarchy);
    return <div className="records">
        {list.map((r, i) => <Breadcrumb relatives={r} first={i == 0} last={i == list.length - 1}/>)}
    </div>;
};

const Breadcrumb = (props: {relatives: RecordSetRelatives.AsObject, first?: boolean, last?: boolean}) => {
    const self = props.relatives.self;
    return <>
        <span className="record">
            <a href={recordSetHref(self)}>
                {props.first ? self.longtitle : self.shorttitle}
            </a>
        </span>
        {!props.last && " » "}
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