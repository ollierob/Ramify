import * as React from "react";
import {FamilyTree} from "../../../protobuf/generated/family_pb";
import {Input, Popover} from "antd";
import {SearchIcon} from "../../images/Icons";
import {FamilyTreeId} from "../FamilyTree";
import {viewTreePeopleHref} from "../../../pages/people/PeopleLinks";

type SearchState = {popover?: boolean, input?: string};

export const PersonSearchPopover = (props: {tree: FamilyTree.AsObject}) => {

    const [state, setState] = React.useState<SearchState>({});

    return <Popover
        content={<PersonSearchDropdown id={props.tree.id}/>}
        placement="bottomLeft"
        visible={state.popover}
        onVisibleChange={visible => setState({popover: visible})}>
            <span className={"control " + (state.popover ? "red" : "gray")}>
                <SearchIcon/> Find person
            </span>
    </Popover>;

};

const PersonSearchDropdown = (props: {id: FamilyTreeId}) => {

    const [state, setState] = React.useState<SearchState>({});

    return <>
        <Input
            placeholder="Enter a name"
            value={state.input}
            onChange={e => setState({input: e.target.value})}/>

        <p>
            <a href={viewTreePeopleHref(props.id)}>
                List of people
            </a>
        </p>

    </>;

};