import * as React from "react";
import {Icon, List, Tabs} from "antd";
import {NoData} from "../../../style/NoData";
import {FamilyTree} from "../../../../protobuf/generated/family_pb";
import {AsyncData, asyncLoadData} from "../../../fetch/AsyncData";
import {DEFAULT_FAMILY_TREE_LOADER} from "../../../tree/FamilyTreeLoader";
import {Loading} from "../../../style/Loading";
import {createTreeHref, viewTreeHref} from "../../../../pages/people/PeopleLinks";

type Props = {
    open: boolean;
}

type State = {
    activeTab?: string;
    trees: AsyncData<ReadonlyArray<FamilyTree.AsObject>>
}

export class PeopleMenu extends React.PureComponent<Props, State> {

    private readonly treeLoader = DEFAULT_FAMILY_TREE_LOADER;
    private readonly setTrees = () => this.setState({activeTab: "trees"});
    private readonly setRecent = () => this.setState({activeTab: "recent"});
    private readonly setSearch = () => this.setState({activeTab: "search"});

    constructor(props) {
        super(props);
        this.state = {
            activeTab: "search",
            trees: {}
        };
    }

    render() {

        return <div className="tabbedSubmenu">

            <Tabs tabPosition="left" activeKey={this.state.activeTab} size="large">

                <Tabs.TabPane key="search" tab={<TabTitle title="Search" onMouseover={this.setSearch}/>}>
                    <Search/>
                </Tabs.TabPane>

                <Tabs.TabPane key="trees" tab={<TabTitle title="Trees" onMouseover={this.setTrees}/>}>
                    <Trees trees={this.state.trees.data || []} loading={this.state.trees.loading}/>
                </Tabs.TabPane>

                <Tabs.TabPane key="recent" tab={<TabTitle title="Recent" onMouseover={this.setRecent}/>}>
                    <Recent/>
                </Tabs.TabPane>

            </Tabs>

        </div>;

    }

    componentDidMount() {
        this.loadTrees();
    }

    private loadTrees() {
        asyncLoadData(null, this.treeLoader.loadFamilyTrees, trees => this.setState({trees}));
    }

}

const TabTitle = (props: {title: React.ReactNode, onMouseover: () => void}) => {
    return <div onMouseOver={props.onMouseover}>{props.title}</div>;
};

const Search = () => {
    return <></>;
};

type TreeListItem = {create?: boolean, loading?: boolean, text?: string, id?: string, count?: number};

const Trees = (props: {trees: ReadonlyArray<FamilyTree.AsObject>, loading: boolean}) => {

    const list: TreeListItem[] = [{create: true}];
    if (props.trees) list.push(...props.trees.map<TreeListItem>(t => ({text: t.name, id: t.id, count: t.numpeople})));
    if (props.loading) list.push({loading: true});

    return <List
        className="treeList"
        dataSource={list}
        renderItem={l => <List.Item>
            {l.create && <a href={createTreeHref()}><Icon type="file-add"/> Create new tree</a>}
            {l.loading && <><Loading/> Loading</>}
            {l.id && <FamilyTreeLink item={l}/>}
        </List.Item>}/>;

};

const FamilyTreeLink = (props: {item: TreeListItem}) => {
    return <>
        <a href={viewTreeHref(props.item.id)}>{props.item.text || <i>Unnamed tree</i>}</a>
        <span className="unimportant" style={{marginLeft: "0.5em"}}>{props.item.count.toLocaleString()} people</span>
    </>;
};

const Recent = (props: {}) => {
    return <NoData text="No recent people"/>;
};