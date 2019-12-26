import * as React from "react";
import {Place} from "../../protobuf/generated/place_pb";
import {Card} from "antd";
import {RecordSet} from "../../protobuf/generated/record_pb";
import {placeHref} from "../../pages/places/PlaceLinks";
import "./RecordCards.css";
import {HasClass} from "../style/HasClass";
import {Loading} from "../style/Loading";
import {sortRecordSetByTitle} from "./RecordSet";
import {RecordSetCard} from "./RecordSetCard";

export type RecordSetTitling = {
    shortTitle?: boolean;
    removePrefix?: string;
}

type Props = HasClass & RecordSetTitling & {
    loading?: boolean
    records: ReadonlyArray<RecordSet.AsObject>
    alsoSee?: ReadonlyArray<Place.AsObject>
    fixedWidth?: boolean;
    ignoreNone?: boolean;
}

export class RecordSetCards extends React.PureComponent<Props> {

    render() {

        if (!this.props.records) return null;

        const records = [...this.props.records].sort(sortRecordSetByTitle);
        //if (props.groupByParent && props.relatives && props.relatives.length) return <GroupedRecordCards {...props}/>;

        return <div
            className={"recordCards" + (this.props.fixedWidth ? " fixedWidth" : "")}
            style={this.props.style}>

            {this.props.loading && <Loading/>}

            {records.map(record => <RecordSetCard {...this.props} key={record.id} record={record}/>)}

            {!this.props.loading && !records.length && !this.props.ignoreNone && <NoRecordCards/>}

            {this.props.alsoSee && <AlsoSeeCard alsoSee={this.props.alsoSee}/>}

        </div>;

    }

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

export const NoRecordCards = () => {
    return <>No records known.</>;
};
