import * as React from "react";
import {SubMenu} from "../../pages/SubMenu";
import {AsyncData} from "../fetch/AsyncData";
import {FamilyTree} from "../../protobuf/generated/family_pb";
import {Loading} from "../style/Loading";
import {FavouritesIcon, SearchIcon, TreeIcon} from "../images/Icons";
import {Input, Popover} from "antd";
import {FamilyTreeId} from "./FamilyTree";

export const FamilyTreeSubmenu = (props: {tree: AsyncData<FamilyTree.AsObject>}) => {
    return <SubMenu>
        {props.tree.loading && <Loading/>}
        {props.tree.data && <FamilyTreeTitle tree={props.tree.data}/>}
    </SubMenu>;
};

type TitleState = {search?: boolean, favourites?: boolean}

const FamilyTreeTitle = (props: {tree: FamilyTree.AsObject}) => {

    const [state, setState] = React.useState<TitleState>({});

    const tree = props.tree;

    return <>

        <span className="title">
            <TreeIcon/> {tree.name}
        </span>

        <Popover
            content={<PersonSearchDropdown id={tree.id}/>}
            placement="bottomLeft"
            visible={state.search}
            onVisibleChange={v => setState({search: v, favourites: false})}>
            <span className={"control " + (state.search ? "red" : "gray")}>
                <SearchIcon/> Find person
            </span>
        </Popover>

        <Popover
            content={<PersonBookmarkDropdown id={tree.id}/>}
            placement="bottomLeft"
            visible={state.favourites}
            onVisibleChange={v => setState({favourites: v, search: false})}>
            <span className={"control " + (state.favourites ? "red" : "gray")}>
                <FavouritesIcon/> Favourites
            </span>
        </Popover>

    </>;
};

type SearchState = {input?: string};

const PersonSearchDropdown = (props: {id: FamilyTreeId}) => {

    const [state, setState] = React.useState<SearchState>({});

    return <>
        <Input
            placeholder="Enter a name"
            value={state.input}
            onChange={e => setState({input: e.target.value})}/>
    </>;

};

const PersonBookmarkDropdown = (props: {id: FamilyTreeId}) => {
    return <>
        No favourites yet.
    </>;
};