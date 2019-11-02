import {Person} from "../../../protobuf/generated/person_pb";
import {Event} from "../../../protobuf/generated/event_pb";
import {EnrichedRecord} from "../../../components/records/RecordLoader";

export type IndividualRecord = {
    person: Person.AsObject;
    birth?: Event.AsObject;
    baptism?: Event.AsObject;
    residence?: ReadonlyArray<Event.AsObject>;
    mention?: ReadonlyArray<Event.AsObject>
    death?: Event.AsObject;
    burial?: Event.AsObject;
    image?: string;
    record: EnrichedRecord;
}