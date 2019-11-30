import * as React from "react";
import {PlaceBasePage, PlaceBasePageProps} from "../PlaceBasePage";
import {AsyncData, asyncLoadData} from "../../../components/fetch/AsyncData";
import {PlaceId} from "../../../components/places/Place";
import {DEFAULT_PLACE_LOADER} from "../../../components/places/PlaceLoader";
import {PlaceGroupInfo} from "./PlaceGroupInfo";
import "./PlaceGroup.css";
import {updatePageHash} from "../../../components/Page";
import {PlaceGroupId, ResolvedPlaceGroup, synthesizePlaceGroup} from "../../../components/places/PlaceGroup";
import {addPlaceHistory} from "../../../components/places/PlaceHistory";

type Props = PlaceBasePageProps;

type State = {
    groupId: PlaceGroupId;
    group: AsyncData<ResolvedPlaceGroup>;
    placeId: PlaceId;
};

export class PlaceGroupPage extends PlaceBasePage<State> {

    private readonly placeLoader = DEFAULT_PLACE_LOADER;

    constructor(props: Props) {
        super(props);
        this.state = {
            groupId: this.readPlaceGroupId(),
            group: {loading: true},
            placeId: this.readPlaceId(),
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
        this.setState({placeId: id});
        updatePageHash({base: "group", place: id});
    }

    body() {

        return <div className="content leftRest group">
            <PlaceGroupInfo
                {...this.state}
                favourites={this.placeFavourites}
                selected={this.state.placeId}
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
        if (this.state.groupId != prevState.groupId || (!this.state.groupId && this.state.placeId != prevState.placeId))
            this.loadPlaceGroup();
        if (this.state.group.data && this.state.group.data != prevState.group.data)
            this.setPageTitle(this.state.group.data.group.name);
    }

    protected hashChanged(): void {
        this.updatePlace();
    }

    private updatePlace() {
        this.setState({groupId: this.readPlaceGroupId(), placeId: this.readPlaceId()});
    }

    private loadPlaceGroup(groupId: PlaceGroupId = this.state.groupId, placeId: PlaceId = this.state.placeId) {
        if (groupId) asyncLoadData(groupId, id => this.placeLoader.loadResolvedGroup(id), group => this.setState({group}));
        else if (placeId) asyncLoadData(placeId, id => this.loadPlace(id), group => this.setState({group}));
    }

    private loadPlace(id: PlaceId): Promise<ResolvedPlaceGroup> {
        return this.placeLoader.loadPlaceBundle(id).then(synthesizePlaceGroup);
    }

}