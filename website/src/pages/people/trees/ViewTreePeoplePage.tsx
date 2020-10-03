import * as React from "react";
import {PeopleBasePage} from "../PeopleBasePage";
import {AsyncData, asyncLoadData} from "../../../components/fetch/AsyncData";
import {FamilyTreeViewSubmenu} from "../../../components/tree/view/FamilyTreeViewSubmenu";
import {FamilyTreeId} from "../../../components/tree/FamilyTree";
import {DEFAULT_FAMILY_TREE_LOADER} from "../../../components/tree/FamilyTreeLoader";
import {FamilyTree} from "../../../protobuf/generated/family_pb";
import {Person} from "../../../protobuf/generated/person_pb";
import "./ViewTree.css";
import {Table} from "antd";
import {ColumnProps} from "antd/es/table";
import {PersonName} from "../../../components/people/PersonName";
import {birthYear, deathYear} from "../../../components/event/Event";
import {personProfileHref} from "../PeopleLinks";

const flatten = require('arr-flatten');

type State = {
    treeId: string,
    tree: AsyncData<FamilyTree.AsObject>
}

export default class ViewTreePeoplePage extends PeopleBasePage<State> {

    readonly familyTreeLoader = DEFAULT_FAMILY_TREE_LOADER;

    constructor(props) {
        super(props);
        this.state = {
            treeId: this.readTreeId(),
            tree: {loading: true}
        };
    }

    private readTreeId() {
        return this.urlParameter("id");
    }

    componentDidMount() {
        this.loadTree();
    }

    private loadTree(id: FamilyTreeId = this.state.treeId) {
        if (!id) return;
        //Load all people
        asyncLoadData(id, this.familyTreeLoader.loadFamilyTree, tree => {
            this.setState({tree});
        });
    }

    body(): React.ReactNode {
        return <div className="viewTree">
            <FamilyTreeViewSubmenu tree={this.state.tree} link/>
            <div className="content">
                <FamilyTreePeopleTable tree={this.state.tree}/>
            </div>
        </div>;
    }

}

type MembersProps = {
    tree: AsyncData<FamilyTree.AsObject>
}

type MembersState = {
    page: number,
    loading: boolean,
    data: PersonInTree[]
}

class FamilyTreePeopleTable extends React.PureComponent<MembersProps, MembersState> {

    readonly pageSize = 25;

    constructor(props) {
        super(props);
        this.state = {
            page: 1,
            loading: props.tree.loading,
            data: readPeople(props.tree.data)
        };
    }

    render() {

        return <>

            <Table
                loading={this.state.loading}
                dataSource={this.state.data}
                columns={columns}
                pagination={{position: "top"}}/>

        </>;

    }

    componentDidUpdate(prevProps: Readonly<MembersProps>) {
        if (this.props.tree != prevProps.tree)
            this.setState({loading: this.props.tree.loading, data: readPeople(this.props.tree.data)});
    }

}

function readPeople(tree: FamilyTree.AsObject): PersonInTree[] {
    if (!tree) return [];
    const people: Person.AsObject[] = flatten(tree.familyList.map(f => f.personList));
    return people.map<PersonInTree>(person => ({person: person, tree: tree}));
}

type PersonInTree = {person: Person.AsObject, tree: FamilyTree.AsObject}

type FamilyTreePeopleTableColumn = ColumnProps<PersonInTree>
const columns: FamilyTreePeopleTableColumn[] = [
    {
        key: "name",
        title: "Name",
        sorter: (p1, p2) => (p1.person?.name?.value || "").localeCompare(p2.person?.name?.value || ""),
        render: (t, p) => <a href={personProfileHref(p.tree.id, p.person.id)}><PersonName name={p.person.name}/></a>
    },
    {
        key: "birth",
        title: "Birth year",
        sorter: true,
        render: (t, p) => birthYear(p.person.eventsList)
    },
    {
        key: "death",
        title: "Death year",
        sorter: true,
        render: (t, p) => deathYear(p.person.eventsList)
    }
];