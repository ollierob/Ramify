import * as React from "react";
import {SubMenu} from "../../SubMenu";
import {Person} from "../../../protobuf/generated/person_pb";
import {FamilyTree} from "../../../protobuf/generated/family_pb";
import {TreeIcon} from "../../../components/images/Icons";
import {viewTreeHref} from "../PeopleLinks";
import {PersonSearchPopover} from "../../../components/tree/view/PersonSearchPopover";

export const PersonProfileSubmenu = (props: {person: Person.AsObject, tree: FamilyTree.AsObject, loading: boolean}) => {
    return <SubMenu className="top">
        {props.person && props.tree && <PersonProfileSubmenuContent {...props}/>}
    </SubMenu>;
};

const PersonProfileSubmenuContent = (props: {person: Person.AsObject, tree: FamilyTree.AsObject}) => {
    return <>
        <span className="tree">
            <a href={viewTreeHref(props.tree.id)}>
                <TreeIcon/> {props.tree.name}
            </a>
        </span>

        <PersonSearchPopover {...props}/>

    </>;
};