import {Person} from "../../protobuf/generated/person_pb";
import {isFemale, isMale} from "../people/Gender";
import {PersonId} from "../people/PersonId";
import {Relatives} from "./Relatives";

export type RelativeRelationship = "self" | "parent" | "spouse" | "child" | "sibling"

export function determineRelationship(id: PersonId, relatives: Relatives): RelativeRelationship {
    if (!id || !relatives) return null;
    if (id == relatives.father?.id || id == relatives.mother?.id) return "parent";
    for (const s of relatives.spouses) {
        if (s.spouse && s.spouse.id == id) return "spouse";
        for (const c of s.children) {
            if (c.id == id) return "child";
        }
    }
    for (const s of relatives.siblings) {
        if (id == s.id) return "sibling";
    }
    return null;
}

export function renderRelationship(relationship: RelativeRelationship, person: Person.AsObject): string {
    switch (relationship) {
        case "parent":
            if (isMale(person)) return "father";
            if (isFemale(person)) return "mother";
            return "parent";
        case "spouse":
            if (isMale(person)) return "husband";
            if (isFemale(person)) return "wife";
            return "spouse";
        case "child":
            if (isMale(person)) return "son";
            if (isFemale(person)) return "daughter";
            return "child";
        case "sibling":
            if (isMale(person)) return "brother";
            if (isFemale(person)) return "sister";
            return "sibling";
        default:
            return null;
    }
}