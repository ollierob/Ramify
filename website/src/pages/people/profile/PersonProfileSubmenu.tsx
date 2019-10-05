import * as React from "react";
import {SubMenu} from "../../SubMenu";
import {Person} from "../../../protobuf/generated/person_pb";
import {FamilyTree} from "../../../protobuf/generated/family_pb";
import {PersonIcon, TreeIcon} from "../../../components/images/Icons";
import {nameToString} from "../../../components/people/Name";

export const PersonProfileSubmenu = (props: {person: Person.AsObject, tree: FamilyTree.AsObject, loading: boolean}) => {
    return <SubMenu>
        {props.person && props.tree && <SubmenuContent {...props}/>}
    </SubMenu>;
};

const SubmenuContent = (props: {person: Person.AsObject, tree: FamilyTree.AsObject}) => {
    return <>
        <span className="tree">
            <TreeIcon/> {props.tree.name}
        </span>
        <span className="person">
            <PersonIcon/> {nameToString(props.person.name)}
        </span>
    </>;
};