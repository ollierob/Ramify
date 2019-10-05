import {FamilyTreeId} from "../../components/tree/FamilyTree";

export function createTreeHref() {
    return peoplePrefix() + "#/tree/create";
}

export function viewTreeHref(id: FamilyTreeId) {
    return peoplePrefix() + "#/tree/view?id=" + id;
}

function peoplePrefix() {
    try {
        const current = window.location.pathname;
        if (current && !current.startsWith("/people")) return "/people";
    } catch (e) {
    }
    return "";
}