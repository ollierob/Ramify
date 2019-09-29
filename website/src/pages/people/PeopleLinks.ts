export function createTreeHref() {
    return peoplePrefix() + "#/tree/create";
}

function peoplePrefix() {
    try {
        const current = window.location.pathname;
        if (current && !current.startsWith("/people")) return "/people";
    } catch (e) {
    }
    return "";
}