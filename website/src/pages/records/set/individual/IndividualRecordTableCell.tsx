import * as React from "react";
import {IndividualRecord} from "./IndividualRecord";
import {formatDateRange} from "../../../../components/date/DateFormat";
import {PlaceLink} from "../../../../components/places/PlaceLink";

type CellProps = {
    record: IndividualRecord
}

export const DeathDatePlaceCell = (props: CellProps) => {
    const death = props.record.death;
    if (!death) return null;
    return <>
        {death.date && <>{formatDateRange(death.date, "day")}<br/></>}
        {death.place && <>at <PlaceLink place={death.place}/></>}
    </>;

};