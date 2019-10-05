import * as React from "react";
import {SubMenu} from "../../../pages/SubMenu";
import {AsyncData} from "../../fetch/AsyncData";
import {FamilyTree} from "../../../protobuf/generated/family_pb";
import {Loading} from "../../style/Loading";
import {FavouritesIcon, SearchIcon, TreeIcon, ViewIcon} from "../../images/Icons";
import {Button, Icon, Input, Menu, Popover} from "antd";
import {FamilyTreeId} from "../FamilyTree";

export const FamilyTreeSubmenu = (props: {tree: AsyncData<FamilyTree.AsObject>}) => {
    return <SubMenu>
        {props.tree.loading && <Loading/>}
        {props.tree.data && <SubmenuContent tree={props.tree.data}/>}
    </SubMenu>;
};

const SubmenuContent = (props: {tree: FamilyTree.AsObject}) => {

    const tree = props.tree;

    return <>

        <span className="title">
            <TreeIcon/> {tree.name}
        </span>

        <PersonSearch {...props}/>
        <PersonBookmark {...props}/>
        <ViewControls {...props}/>

    </>;
};

type SearchState = {popover?: boolean, input?: string};

const PersonSearch = (props: {tree: FamilyTree.AsObject}) => {

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
    </>;

};

type BookmarkState = {dropdown?: boolean};

const PersonBookmark = (props: {tree: FamilyTree.AsObject}) => {

    const [state, setState] = React.useState<BookmarkState>({});
    const tree = props.tree;

    return <Popover
        content={<PersonBookmarkDropdown id={tree.id}/>}
        placement="bottomLeft"
        visible={state.dropdown}
        onVisibleChange={visible => setState({dropdown: visible})}>
            <span className={"control " + (state.dropdown ? "red" : "gray")}>
                <FavouritesIcon/> Favourites
            </span>
    </Popover>;

};

const PersonBookmarkDropdown = (props: {id: FamilyTreeId}) => {
    return <>
        No favourites yet.
    </>;
};

type ViewState = {dropdown?: boolean};

const ViewControls = (props: {tree: FamilyTree.AsObject}) => {

    const [state, setState] = React.useState<ViewState>({});

    return <Popover
        content={<ViewControlsDropdown/>}
        placement="bottomLeft"
        visible={state.dropdown}
        onVisibleChange={visible => setState({dropdown: visible})}>
        <span className={"control " + (state.dropdown ? "red" : "gray")}>
            <ViewIcon/> Tree view
        </span>
    </Popover>;

};

const ViewControlsDropdown = (props: {}) => {
    return <>
        <Button icon="reload">
            Reset view
        </Button>
        <br/>
        <Button icon="zoom-in">
            Zoom in
        </Button>
        <br/>
        <Button icon="zoom-out">
            Zoom out
        </Button>
    </>;
};

