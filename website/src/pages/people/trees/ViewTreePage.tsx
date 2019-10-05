import * as React from "react";
import {PeopleBasePage} from "../PeopleBasePage";
import {BasePageProps} from "../../BasePage";
import {AsyncData, asyncLoadData} from "../../../components/fetch/AsyncData";
import {Family, FamilyTree} from "../../../protobuf/generated/family_pb";
import {FamilyTreeId} from "../../../components/tree/FamilyTree";
import {FamilyTreeViewer} from "../../../components/tree/view/FamilyTreeViewer";
import "./ViewTree.css";
import {DEFAULT_FAMILY_TREE_LOADER} from "../../../components/tree/FamilyTreeLoader";
import {FamilyTreeSubmenu} from "../../../components/tree/view/FamilyTreeSubmenu";

type Props = BasePageProps;

type State = {
    treeId: string;
    tree: AsyncData<FamilyTree.AsObject>;
    family?: Family.AsObject;
    zoom: number
}

export class ViewTreePage extends PeopleBasePage<State> {

    readonly familyTreeLoader = DEFAULT_FAMILY_TREE_LOADER;
    readonly setZoom = (zoom: number, reset?: boolean) => this.setState({zoom});

    constructor(props) {
        super(props);
        this.state = {
            treeId: this.readTreeId(),
            tree: {loading: true},
            zoom: 0.8
        };
    }

    body() {
        return <div className="viewTree">
            <FamilyTreeSubmenu tree={this.state.tree} zoom={this.state.zoom} setZoom={this.setZoom}/>
            <FamilyTreeViewer treeId={this.state.treeId} family={this.state.family} content zoom={this.state.zoom}/>
        </div>;
    }

    componentDidMount() {
        this.loadTree();
    }

    componentDidUpdate(prevProps: Readonly<Props>, prevState: Readonly<State>) {
        if (this.props.history != prevProps.history)
            this.setState({treeId: this.readTreeId()});
        if (this.state.treeId != prevState.treeId)
            this.loadTree(this.state.treeId);
    }

    private readTreeId() {
        return this.urlParameter("id");
    }

    private loadTree(id: FamilyTreeId = this.state.treeId) {
        if (!id) return;
        asyncLoadData(id, this.familyTreeLoader.loadFamilyTree, tree => {
            this.setState({
                tree: tree,
                family: tree.data ? tree.data.familyList[0] : null
            });
        });
    }

}