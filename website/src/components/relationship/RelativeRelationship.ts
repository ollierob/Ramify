import {Person} from "../../protobuf/generated/person_pb";
import {isFemale, isMale} from "../people/Gender";
import {PersonId} from "../people/PersonId";
import {Relatives} from "./Relatives";

export type RelativeRelationship = "self" | "parent" | "spouse" | "child"

export function determineRelationship(id: PersonId, relatives: Relatives): RelativeRelationship {
    if (!id || !relatives) return null;
    if (id == relatives.father?.id || id == relatives.mother?.id) return "parent";
    for (const s of relatives.spouses) {
        if (id == s.spouse.id) return "spouse";
        for (const c of s.children) {
            if (id == c.id) return "child";
        }
    }
    return null;
}

export type RelationshipNames = {
    father: string,
    mother: string,
    parent: string,
    husband: string,
    wife: string,
    spouse: string,
    son: string,
    daughter: string,
    child: string
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
        default:
            return null;
    }
}