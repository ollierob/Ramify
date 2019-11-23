import * as React from "react";
import {SubMenu} from "../../SubMenu";
import {Person} from "../../../protobuf/generated/person_pb";
import {FamilyTree} from "../../../protobuf/generated/family_pb";
import {PersonIcon, TreeIcon} from "../../../components/images/Icons";
import {viewTreeHref} from "../PeopleLinks";

export const PersonProfileSubmenu = (props: {person: Person.AsObject, tree: FamilyTree.AsObject, loading: boolean}) => {
    return <SubMenu>
        {props.person && props.tree && <SubmenuContent {...props}/>}
    </SubMenu>;
};

const SubmenuContent = (props: {person: Person.AsObject, tree: FamilyTree.AsObject}) => {
    return <>
        <span className="tree">
            <a href={viewTreeHref(props.tree.id)}>
                <TreeIcon/> {props.tree.name}
            </a>
        </span>
        <span className="person">
            <PersonIcon/> {props.person.name.value}
        </span>
    </>;
};