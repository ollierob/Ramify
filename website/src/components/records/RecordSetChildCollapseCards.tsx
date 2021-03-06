import * as React from "react";
import {ChangeEvent} from "react";
import {Place} from "../../protobuf/generated/place_pb";
import {Card, Collapse, Input} from "antd";
import {RecordSet} from "../../protobuf/generated/record_pb";
import {placeHref} from "../../pages/places/PlaceLinks";
import "./RecordCards.css";
import {HasClass} from "../style/HasClass";
import {Loading} from "../style/Loading";
import {sortRecordSetByTitle} from "./RecordSet";
import {RecordSetChildCard} from "./RecordSetChildCard";
import {RecordSetChildCards, RecordSetTitling} from "./RecordSetChildCards";

const {Panel} = Collapse;

type Props = HasClass & RecordSetTitling & {
    loading?: boolean
    records: ReadonlyArray<RecordSet.AsObject>
    alsoSee?: ReadonlyArray<Place.AsObject>
    fixedWidth?: boolean;
    ignoreNone?: boolean;
}

type State = {
    filter?: string
    filtered: RecordSet.AsObject[]
}

export class RecordSetChildCollapseCards extends React.PureComponent<Props, State> {

    private readonly setFilter = (e: ChangeEvent<HTMLInputElement>) => this.setState({
        filter: e.target.value,
        filtered: filterAndSort(this.props.records, e.target.value?.toLowerCase())
    });

    private readonly clearFilter = () => this.setState({
        filter: null,
        filtered: filterAndSort(this.props.records, null)
    });

    constructor(props: Props) {
        super(props);
        this.state = {
            filtered: filterAndSort(props.records, null)
        };
    }

    render() {

        const hide = (!this.props.records || !this.props.records.length) && !this.props.loading;

        return <Collapse
            className="recordCardsCollapse"
            defaultActiveKey={this.props.records?.length <= 3 ? "records" : []}
            style={hide ? {display: "none"} : null}>

            <Panel key="records" header="Child records">

                {this.props.records?.length >= 10 && this.filterInput()}

                <RecordSetChildCards
                    {...this.props}
                    records={this.state.filtered}/>

            </Panel>

        </Collapse>;

    }

    componentDidUpdate(prevProps: Readonly<Props>, prevState: Readonly<State>) {
        if (this.props.records != prevProps.records)
            this.clearFilter();
    }

    private filterInput() {
        return <Input
            className="filter"
            value={this.state.filter}
            onChange={this.setFilter}
            placeholder="Filter"
            maxLength={20}
            width={150}/>;
    }

}

function filterAndSort(records: ReadonlyArray<RecordSet.AsObject>, filter: string): RecordSet.AsObject[] {
    if (!records || !records.length) return [];
    return records.filter(record => filterRecord(record, filter)).sort(sortRecordSetByTitle);
}

function filterRecord(record: RecordSet.AsObject, filter: string) {
    if (!filter) return true;
    return (record.longtitle?.toLowerCase() || "").includes(filter)
        || (record.description?.toLowerCase() || "").includes(filter);
}

const AlsoSeeCard = (props: {alsoSee: ReadonlyArray<Place.AsObject>}) => {
    const alsoSee = props.alsoSee;
    if (!alsoSee || !alsoSee.length) return null;
    return <Card title="Also see" className="recordCard">
        <ul>
            {alsoSee.map(place => <li>
                <a href={placeHref(place)}>{place.name}</a>
                {" "}
                ({place.type.name})
            </li>)}
        </ul>
    </Card>;
};

const NoRecordCards = (props: {clearFilter?: () => void}) => {
    return <>
        No records.
        {props.clearFilter && <> <a onClick={props.clearFilter}>Clear filter</a></>}
    </>;
};
