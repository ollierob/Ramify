import {Name} from "../../protobuf/generated/person_pb";

export function nameToString(name: Name.AsObject): string {
    if (!name) return null;
    return (name.forenameList.join(" ")
        + " "
        + name.surname).trimLeft();
}