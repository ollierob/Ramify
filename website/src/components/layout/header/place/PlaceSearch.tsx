import * as React from "react";
import {ChangeEvent, FormEvent} from "react";
import {AsyncData, asyncLoadData} from "../../../fetch/AsyncData";
import {Place} from "../../../../protobuf/generated/place_pb";
import {DEFAULT_PLACE_LOADER} from "../../../places/PlaceLoader";
import {Button, Form, Input, List} from "antd";
import {SearchIcon} from "../../../images/Icons";
import {PlaceLink} from "../../../places/PlaceLink";
import {PlaceId} from "../../../places/Place";
import RegionCascader from "../../../places/RegionCascader";
import {PlaceAndParent} from "../../../places/PlaceAndParent";

type Props = {
    parentOpen: boolean;
}

type State = {
    searchString?: string;
    searchResults: AsyncData<ReadonlyArray<Place.AsObject>>
    selectedRegion?: PlaceId
};

export class PlaceSearch extends React.PureComponent<Props, State> {

    private readonly placeLoader = DEFAULT_PLACE_LOADER;
    private readonly setSearchString = (e: ChangeEvent<HTMLInputElement>) => this.setState({searchString: e.target.value});
    private readonly setSelectedRegion = (id: PlaceId) => this.setState({selectedRegion: id});

    constructor(props: Props) {
        super(props);
        this.state = {
            searchResults: {}
        };
        this.doSearch = this.doSearch.bind(this);
    }

    render() {

        return <div className="placeSearch">

            <Form onSubmit={this.doSearch}>

                <Form.Item className="search">
                    <Input
                        size="large"
                        placeholder="Place name"
                        value={this.state.searchString}
                        onChange={this.setSearchString}/>
                </Form.Item>

                <Form.Item>

                    <Button
                        htmlType="submit"
                        type="primary"
                        disabled={this.state.searchResults.loading}>
                        <SearchIcon/> Search
                    </Button>

                    <RegionCascader
                        parentOpen={this.props.parentOpen}
                        placeLoader={this.placeLoader}
                        onSelect={this.setSelectedRegion}/>

                </Form.Item>

            </Form>

            <div className="results">
                <SearchResults searchResults={this.state.searchResults}/>
            </div>

        </div>;

    }

    private doSearch(e?: FormEvent<HTMLFormElement>) {
        if (e) e.preventDefault();
        const name = this.state.searchString;
        if (!name) return;
        asyncLoadData(name, n => this.placeLoader.findPlaces(n, {limit: 10, within: this.state.selectedRegion}), searchResults => this.setState({searchResults}));
    }

    componentDidUpdate(prevProps: Readonly<Props>, prevState: Readonly<State>) {
        if (this.state.searchResults.data != prevState.searchResults.data)
            console.log("Search results: " + this.state.searchString + " => " + (this.state.searchResults.data ? this.state.searchResults.data.length : 0));
    }

}

const SearchResults = (props: {searchResults: AsyncData<ReadonlyArray<Place.AsObject>>}) => {

    const results = props.searchResults;

    if (results.data) {
        const data: Place.AsObject[] = [...results.data];
        return <List
            loading={results.loading}
            dataSource={data}
            renderItem={place => <List.Item key={place.id}><SearchResult place={place}/></List.Item>}/>;
    }

    return null;

};

const SearchResult = (props: {place: Place.AsObject}) => {
    return <PlaceAndParent {...props}/>;
};