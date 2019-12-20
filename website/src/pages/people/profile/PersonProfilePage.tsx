import * as React from "react";
import {PeopleBasePage} from "../PeopleBasePage";
import "./PersonProfile.css";
import {PersonProfileSubmenu} from "./PersonProfileSubmenu";
import {AsyncData, asyncLoadData} from "../../../components/fetch/AsyncData";
import {Person} from "../../../protobuf/generated/person_pb";
import {PersonId} from "../../../components/people/PersonId";
import {Family, FamilyTree} from "../../../protobuf/generated/family_pb";
import {FamilyTreeId, findPersonInTree} from "../../../components/tree/FamilyTree";
import {BasePageProps} from "../../BasePage";
import {DEFAULT_FAMILY_TREE_LOADER} from "../../../components/tree/FamilyTreeLoader";
import {PersonProfile} from "./PersonProfile";

type Props = BasePageProps;

type IdState = {
    personId: PersonId;
    treeId: FamilyTreeId
}

type State = IdState & {
    tree: AsyncData<FamilyTree.AsObject>
    person?: Person.AsObject
    family?: Family.AsObject;
}

export default class PersonProfilePage extends PeopleBasePage<State> {

    private readonly treeLoader = DEFAULT_FAMILY_TREE_LOADER;

    constructor(props: Props) {
        super(props);
        this.state = {
            ...this.readIds(),
            tree: {loading: true}
        };
    }

    body() {

        return <div className="personProfile">

            <PersonProfileSubmenu
                person={this.state.person}
                tree={this.state.tree.data}
                loading={this.state.tree.loading}/>

            <div className="content">
                <PersonProfile
                    loading={this.state.tree.loading}
                    person={this.state.person}
                    family={this.state.family}
                    tree={this.state.treeId}/>
            </div>

        </div>;

    }

    private readIds(): IdState {
        return {
            personId: this.urlParameter("personId"),
            treeId: this.urlParameter("treeId")
        };
    }

    componentDidMount() {
        this.loadTree();
    }

    componentDidUpdate(prevProps: Readonly<BasePageProps>, prevState: Readonly<State>, snapshot?: any): void {
        if (this.props.location != prevProps.location)
            this.updatePerson();
        if (this.state.personId != prevState.personId || this.state.treeId != prevState.treeId)
            this.loadTree(this.state.treeId, this.state.personId);
        if (this.state.tree.data && this.state.tree.data != prevState.tree.data) {
            const find = findPersonInTree(this.state.personId, this.state.tree.data);
            this.setState({family: find && find.family, person: find && find.person});
        }
        if (this.state.person != prevState.person && this.state.person && this.state.person.name)
            this.setPageTitle(this.state.person.name.value);
    }

    private updatePerson() {
        this.setState({...this.readIds()});
    }

    protected hashChanged(): void {
        this.updatePerson();
    }

    private loadTree(treeId = this.state.treeId, personId = this.state.personId) {
        if (!treeId || !personId) return;
        asyncLoadData(null, () => this.treeLoader.loadFamilyTree(treeId, personId), tree => this.setState({tree}));
    }

}