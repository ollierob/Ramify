import * as React from "react";
import {SubMenu} from "../../pages/SubMenu";
import {AsyncData} from "../fetch/AsyncData";
import {FamilyTree} from "../../protobuf/generated/family_pb";
import {Loading} from "../style/Loading";
import {SearchIcon, TreeIcon} from "../images/Icons";
import {Dropdown, Input, Popover} from "antd";

export const FamilyTreeSubmenu = (props: {tree: AsyncData<FamilyTree.AsObject>}) => {
    return <SubMenu>
        {props.tree.loading && <Loading/>}
        {props.tree.data && <FamilyTreeTitle tree={props.tree.data}/>}
    </SubMenu>;
};

type TitleState = {dropdownActive?: boolean}

const FamilyTreeTitle = (props: {tree: FamilyTree.AsObject}) => {

    const [state, setState] = React.useState<TitleState>({});

    const tree = props.tree;

    return <>

        <span className="title">
            <TreeIcon/> {tree.name}
        </span>

        <Popover
            content={<PersonSearchDropdown {...props}/>}
            placement="bottomLeft"
            visible={state.dropdownActive}
            onVisibleChange={v => setState({dropdownActive: v})}>

            <span className={"control " + (state.dropdownActive ? "red" : "gray")}>
                <SearchIcon/> Find person
            </span>

        </Popover>

    </>;
};

type SearchState = {input?: string};

const PersonSearchDropdown = (props: {tree: FamilyTree.AsObject}) => {

    const [state, setState] = React.useState<SearchState>({});

    return <>
        <Input
            value={state.input}
            onChange={e => setState({input: e.target.value})}/>
    </>;
    
};