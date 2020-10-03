import * as React from "react";
import {SubMenu} from "../../../pages/SubMenu";
import {AsyncData} from "../../fetch/AsyncData";
import {FamilyTree} from "../../../protobuf/generated/family_pb";
import {Loading} from "../../style/Loading";
import {FavouritesIcon, TreeIcon, ViewIcon} from "../../images/Icons";
import {Button, Popover} from "antd";
import {FamilyTreeId} from "../FamilyTree";
import {PersonSearchPopover} from "./PersonSearchPopover";
import {viewTreeHref} from "../../../pages/people/PeopleLinks";

type ZoomHandler = {zoom?: number, setZoom?: (zoom: number, reset?: boolean) => void}

export const FamilyTreeViewSubmenu = (props: {tree: AsyncData<FamilyTree.AsObject>, link?: boolean} & ZoomHandler) => {
    return <SubMenu className="top">
        {props.tree.loading && <Loading/>}
        {props.tree.data && <FamilyTreeViewSubmenuContent {...props} link={props.link} tree={props.tree.data}/>}
    </SubMenu>;
};

const FamilyTreeViewSubmenuContent = (props: {tree: FamilyTree.AsObject, link: boolean} & ZoomHandler) => {

    const tree = props.tree;

    return <>

        <span className="title">
            {props.link
                ? <a href={viewTreeHref(props.tree.id)}><TreeIcon/> {tree.name}</a>
                : <><TreeIcon/> {tree.name}</>}
        </span>

        <PersonSearchPopover {...props}/>
        <PersonBookmark {...props}/>
        {props.setZoom && <ViewControls {...props}/>}

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

const ViewControls = (props: {tree: FamilyTree.AsObject} & ZoomHandler) => {

    const [state, setState] = React.useState<ViewState>({});

    return <Popover
        content={<ViewControlsDropdown {...props}/>}
        placement="bottomLeft"
        visible={state.dropdown}
        onVisibleChange={visible => setState({dropdown: visible})}>
        <span className={"control " + (state.dropdown ? "red" : "gray")}>
            <ViewIcon/> Tree view
        </span>
    </Popover>;

};

const ViewControlsDropdown = (props: {} & ZoomHandler) => {
    return <>
        <Button
            icon="reload"
            onClick={e => props.setZoom(1, true)}>
            Reset view
        </Button>
        <br/>
        <Button
            icon="zoom-in"
            onClick={e => props.setZoom(props.zoom + 0.1)}>
            Zoom in
        </Button>
        <br/>
        <Button
            disabled={props.zoom <= 0.1}
            icon="zoom-out"
            onClick={e => props.setZoom(props.zoom - 0.1)}>
            Zoom out
        </Button>
    </>;
};

