import {Person} from "../../../../protobuf/generated/person_pb";
import {Event} from "../../../../protobuf/generated/event_pb";
import {EnrichedIndividualRecord} from "../../../../components/records/RecordLoader";

export type IndividualRecord = {
    person: Person.AsObject;
    birth?: Event.AsObject;
    baptism?: Event.AsObject;
    residence?: ReadonlyArray<Event.AsObject>;
    mention?: ReadonlyArray<Event.AsObject>
    death?: Event.AsObject;
    burial?: Event.AsObject;
    image?: string;
    record: EnrichedIndividualRecord;
}

export type IndividualRecordProperties = {
    hasBirth?: boolean;
    hasBaptism?: boolean;
    hasResidence?: boolean;
    hasMention?: boolean;
    hasDeath?: boolean;
    hasBurial?: boolean;
}