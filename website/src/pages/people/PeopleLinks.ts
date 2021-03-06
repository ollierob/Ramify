import {FamilyTreeId} from "../../components/tree/FamilyTree";
import {PersonId} from "../../components/people/PersonId";

export function personSearchHref(treeId: FamilyTreeId, personId: PersonId): string {
    return peoplePrefix() + "#/search?personId=" + personId + "&treeId=" + treeId;
}

export function personProfileHref(treeId: FamilyTreeId, personId: PersonId): string {
    return peoplePrefix() + "#/profile?personId=" + personId + "&treeId=" + treeId;
}

export function createTreeHref() {
    return peoplePrefix() + "#/tree/create";
}

export function viewTreeHref(id: FamilyTreeId) {
    return peoplePrefix() + "#/tree/view?id=" + id;
}

export function viewTreePeopleHref(id: FamilyTreeId) {
    return peoplePrefix() + "#/tree/people?id=" + id;
}

function peoplePrefix() {
    try {
        const current = window.location.pathname;
        if (current && !current.startsWith("/people")) return "/people";
    } catch (e) {
    }
    return "";
}