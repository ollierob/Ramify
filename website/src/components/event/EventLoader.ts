import {PlaceId} from "../places/Place";
import {Date} from "../../protobuf/generated/date_pb";
import {Event, EventList} from "../../protobuf/generated/event_pb";
import {protoGet} from "../fetch/ProtoFetch";
import {isoDate} from "../date/DateFormat";
import {queryParameters} from "../fetch/Fetch";

export interface EventLoader {

    loadHistoricEvents(from: Date.AsObject, to: Date.AsObject, places: Set<PlaceId>): Promise<ReadonlyArray<Event.AsObject>>

}

class ProtoEventLoader implements EventLoader {

    loadHistoricEvents(from: Date.AsObject, to: Date.AsObject, places: Set<PlaceId>) {
        const url = "/events/historic/" + isoDate(from) + "/" + isoDate(to)
            + queryParameters({place: Array.from(places)});
        return protoGet(url, EventList.deserializeBinary)
            .then(readEvents);
    }

}

export const DEFAULT_EVENT_LOADER: EventLoader = new ProtoEventLoader();

function readEvents(events: EventList): ReadonlyArray<Event.AsObject> {
    return events ? events.getEventList().map(e => e.toObject()) : [];
}