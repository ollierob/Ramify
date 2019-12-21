import * as React from "react";
import {PlaceBasePage, PlaceBasePageProps} from "../PlaceBasePage";
import {AsyncData, asyncLoadData} from "../../../components/fetch/AsyncData";
import {PlaceId} from "../../../components/places/Place";
import {DEFAULT_PLACE_LOADER} from "../../../components/places/PlaceLoader";
import {PlaceGroupInfo} from "./PlaceGroupInfo";
import "./PlaceGroup.css";
import {updatePageHash} from "../../../components/Page";
import {PlaceGroupId, ResolvedPlaceGroup, synthesizePlaceGroup} from "../../../components/places/PlaceGroup";

type Props = PlaceBasePageProps;

type State = {
    selectedGroupId: PlaceGroupId;
    selectedPlaceId: PlaceId;
    group: AsyncData<ResolvedPlaceGroup>;
};

export class PlaceGroupPage extends PlaceBasePage<State> {

    private readonly placeLoader = DEFAULT_PLACE_LOADER;

    constructor(props: Props) {
        super(props);
        this.state = {
            selectedGroupId: this.readPlaceGroupId(),
            group: {loading: true},
            selectedPlaceId: this.readPlaceId(),
        };
        this.setPlaceId = this.setPlaceId.bind(this);
    }

    private readPlaceGroupId(): PlaceGroupId {
        return this.urlParameter("id");
    }

    private readPlaceId(): PlaceId {
        return this.urlParameter("place");
    }

    private setPlaceId(id: PlaceId) {
        this.setState({selectedPlaceId: id});
        updatePageHash({base: "group", place: id});
    }

    body() {

        return <div className="content leftRest group">
            <PlaceGroupInfo
                {...this.state}
                favourites={this.placeFavourites}
                selected={this.state.selectedPlaceId}
                select={this.setPlaceId}/>
        </div>;

    }

    componentDidMount() {
        super.componentDidMount();
        this.loadPlaceGroup();
    }

    componentDidUpdate(prevProps: Readonly<Props>, prevState: Readonly<State>) {
        if (this.props.location != prevProps.location)
            this.updatePlace();
        if (this.state.selectedGroupId != prevState.selectedGroupId || (!this.state.selectedGroupId && this.state.selectedPlaceId != prevState.selectedPlaceId))
            this.loadPlaceGroup();
        if (this.state.group.data && this.state.group.data != prevState.group.data)
            this.setPageTitle(this.state.group.data.group.name);
        if (this.state.selectedPlaceId != prevState.selectedPlaceId || this.state.group != prevState.group)
            this.addPlaceHistory(this.state.selectedPlaceId);
    }

    protected hashChanged(): void {
        this.updatePlace();
    }

    private updatePlace() {
        this.setState({selectedGroupId: this.readPlaceGroupId(), selectedPlaceId: this.readPlaceId()});
    }

    private loadPlaceGroup(groupId: PlaceGroupId = this.state.selectedGroupId, placeId: PlaceId = this.state.selectedPlaceId) {
        if (groupId) asyncLoadData(groupId, id => this.placeLoader.loadGroup(id), group => this.setState({group}));
        else if (placeId) asyncLoadData(placeId, id => this.loadPlace(id), group => this.setState({group}));
    }

    private loadPlace(id: PlaceId): Promise<ResolvedPlaceGroup> {
        return this.placeLoader.loadPlaceBundle(id).then(synthesizePlaceGroup);
    }

    private addPlaceHistory(selectedPlaceId: PlaceId) {
        if (!selectedPlaceId) return;
        const data = this.state.group.data;
        if (!data) return;
        console.log("Adding place: " + selectedPlaceId);
        const child = data.children.find(p => p.place.id == selectedPlaceId);
        if (child) this.placeHistory.addPlaceHistory(child.place);
    }

}