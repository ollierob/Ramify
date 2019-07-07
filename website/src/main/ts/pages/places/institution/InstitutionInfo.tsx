import * as React from "react";
import {DateRange} from "../../../components/date/DateRange";
import {Card} from "antd";
import {Institution} from "../../../protobuf/generated/institution_pb";
import {HasClass} from "../../../components/style/HasClass";
import {Place, PlaceDescription} from "../../../protobuf/generated/place_pb";
import {RecordCards} from "./InstitutionRecordCards";
import {PlaceTitle} from "../../../components/places/PlaceTitle";

type Props = HasClass & {
    place: Place.AsObject;
    description: PlaceDescription.AsObject;
    institution: Institution.AsObject;
    children?: React.ReactNode
};

export const InstitutionInfo = (props: Props) => {

    const institution = props.institution;
    const description = props.description;

    if (!props.place) return null;

    return <Card
        className={"info " + (props.className || "")}
        style={props.style}
        title={<PlaceTitle {...props}/>}>

        <div className="description">
            {institution && <div className="dates">
                {institution.established && <div>Founded <DateRange date={institution.established}/></div>}
                {institution && institution.closed && <div>Closed <DateRange date={institution.closed}/></div>}
            </div>}
            <div>
                {description ? description.description : <i>No description available.</i>}
            </div>
        </div>

        {props.children}

        {institution && <RecordCards
            records={institution.recordsetList}
            alsoSee={description && description.alsoseeList}/>}

    </Card>

};