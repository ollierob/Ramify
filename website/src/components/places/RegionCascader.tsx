import * as React from "react";
import {Cascader} from "antd";
import {PlaceId} from "./Place";
import {CascaderOptionType} from "antd/es/cascader";
import {Flag} from "../images/Flag";
import {PlaceLoader} from "./PlaceLoader";
import {Place} from "../../protobuf/generated/place_pb";

type Props = {
    placeHolder?: string;
    placeLoader: PlaceLoader;
    onSelect: (id: PlaceId) => void;
    size?: "large" | "default" | "small"
}

type State = {
    selectedRegion: PlaceId[];
    regions: CascaderOptionType[];
    loadingCountries?: boolean;
}

export default class RegionCascader extends React.PureComponent<Props, State> {

    private readonly setSelectedRegion = (id: PlaceId[]) => this.setState({selectedRegion: id});

    constructor(props: Props) {
        super(props);
        this.state = {
            selectedRegion: [],
            regions: [],
            loadingCountries: true
        };
        this.loadLeafPlace = this.loadLeafPlace.bind(this);
        this.renderPlace = this.renderPlace.bind(this);
    }

    render() {

        return <Cascader
            placeholder={this.props.placeHolder || "Matching all places"}
            size={this.props.size || "large"}
            changeOnSelect
            value={this.state.selectedRegion}
            onChange={this.setSelectedRegion}
            options={this.state.regions}
            loadData={this.loadLeafPlace}
            displayRender={this.renderPlace}/>;

    }

    componentDidMount() {
        this.props.placeLoader.loadCountries().then(countries => this.setState({
            loadingCountries: false,
            regions: generateCountryOptions(countries)
        }));
    }

    componentDidUpdate(prevProps: Readonly<Props>, prevState: Readonly<State>) {
        if (this.state.selectedRegion != prevState.selectedRegion)
            this.props.onSelect(this.state.selectedRegion.length ? this.state.selectedRegion[this.state.selectedRegion.length - 1] : null);
    }

    private renderPlace(places: PlaceId[]): React.ReactNode {
        if (!places.length) return null;
        return places[places.length - 1];
    }

    private loadLeafPlace(selected: CascaderOptionType[]) {
        const target = selected[selected.length - 1];
        if (target.depth == 1) {
            target.loading = true;
            this.props.placeLoader.loadChildren(target.value, 1)
                .then(children => {
                    target.loading = false;
                    target.children = children.map<CascaderOptionType>(child => ({
                        label: <><Flag iso={child.iso}/>{child.name}</>,
                        value: child.id,
                        depth: 2
                    })).sort((o1, o2) => o1.value.localeCompare(o2.value));
                }).then(() => this.setState(current => ({regions: [...current.regions]})));
            this.forceUpdate();
        }
    }

}


function generateCountryOptions(countries: ReadonlyArray<Place.AsObject>): CascaderOptionType[] {
    if (!countries) return [];
    return countries.map<CascaderOptionType>(country => ({
        label: <><Flag iso={country.iso}/>{country.name}</>,
        value: country.id,
        isLeaf: false,
        depth: 1
    })).sort((o1, o2) => o1.value.localeCompare(o2.value));
}
