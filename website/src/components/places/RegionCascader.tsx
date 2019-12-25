import * as React from "react";
import {Cascader} from "antd";
import {PlaceId} from "./Place";
import {CascaderOptionType} from "antd/es/cascader";
import {Flag} from "../images/Flag";
import {PlaceLoader} from "./PlaceLoader";
import {Place} from "../../protobuf/generated/place_pb";

type Props = {
    maxDepth: number;
    placeHolder?: string;
    placeLoader: PlaceLoader;
    onSelect: (id: PlaceId) => void;
    size?: "large" | "small"
    parentOpen?: boolean;
}

type State = {
    selectedRegion: PlaceId[];
    regions: CascaderOptionType[];
    visible?: boolean;
}

export default class RegionCascader extends React.PureComponent<Props, State> {

    private readonly setSelectedRegion = (id: PlaceId[]) => this.setState({selectedRegion: id});
    private readonly setVisible = (visible: boolean) => this.setState({visible});

    constructor(props: Props) {
        super(props);
        this.state = {
            selectedRegion: [],
            regions: [],
        };
        this.loadLeafPlace = this.loadLeafPlace.bind(this);
        this.renderPlace = this.renderPlace.bind(this);
    }

    render() {

        return <Cascader
            className="regionCascader"
            placeholder={this.props.placeHolder || "Matching all places"}
            size={this.props.size}
            changeOnSelect
            value={this.state.selectedRegion}
            onChange={this.setSelectedRegion}
            options={this.state.regions}
            loadData={this.loadLeafPlace}
            displayRender={this.renderPlace}
            popupVisible={this.state.visible}
            onPopupVisibleChange={this.setVisible}/>;

    }

    componentDidMount() {
        this.props.placeLoader.loadCountries({onlyTopLevel: true})
            .then(countries => this.setState({
                regions: generateOptions(countries)
            }));
    }

    componentDidUpdate(prevProps: Readonly<Props>, prevState: Readonly<State>) {
        if (this.state.selectedRegion != prevState.selectedRegion)
            this.props.onSelect(this.state.selectedRegion.length ? this.state.selectedRegion[this.state.selectedRegion.length - 1] : null);
        if (!this.props.parentOpen && prevProps.parentOpen && this.state.visible)
            this.setState({visible: false});
    }

    private renderPlace(places: PlaceId[]): React.ReactNode {
        if (!places.length) return null;
        return places[places.length - 1];
    }

    private loadLeafPlace(selected: CascaderOptionType[]) {
        const depth = selected.length;
        const target = selected[depth - 1]; //Select the lowest-selected place
        if (depth < this.props.maxDepth && !target.loadedChildren && !target.loading) {
            target.loading = true;
            target.loadedChildren = false;
            //Ugly but how Ant recommends doing it
            this.props.placeLoader.loadChildren(target.value, 1)
                .then(children => {
                    target.loading = false;
                    target.children = children.map<CascaderOptionType>(child => ({
                        label: <><Flag iso={child.iso}/>{child.name}</>,
                        value: child.id,
                        isLeaf: depth + 1 >= this.props.maxDepth
                    })).sort((o1, o2) => o1.value.localeCompare(o2.value));
                }).then(() => this.setState(current => ({regions: [...current.regions]})));
            this.forceUpdate();
        }
    }

}

function generateOptions(places: ReadonlyArray<Place.AsObject>): CascaderOptionType[] {
    if (!places) return [];
    return places.map<CascaderOptionType>(country => ({
        label: <><Flag iso={country.iso}/>{country.name}</>,
        value: country.id,
        isLeaf: false,
        loadedChildren: false
    })).sort((o1, o2) => o1.value.localeCompare(o2.value));
}
