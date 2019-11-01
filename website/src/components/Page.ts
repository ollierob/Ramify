import {StringMap} from "./Maps";

type QueryableType = string | number | boolean;

export type QueryMap = StringMap<QueryableType> & {base: string}

export function updatePageHash(delta: QueryMap, current: StringMap = readPageHash(), update: (hash: string) => void = setPageHashString): void {
    const total: QueryMap = {...current, ...delta, base: delta.base};
    Object.keys(delta).filter(k => k != "base").forEach(key => {
        const n = delta[key];
        if (!n) delete total[key];
    });
    update(createPageHash(total));
}

export function readPageHash(): StringMap {
    let hash = document.location.hash;
    if (!hash || hash.length <= 1) return {};
    if (hash.charAt(0) == "#") hash = hash.substring(1);
    if (hash.charAt(0) == "/") hash = hash.substring(1);
    const q = hash.indexOf("?");
    const map: StringMap = {base: hash.substring(q)};
    const s = new URLSearchParams(hash.substring(q + 1));
    s.forEach((v, k) => map[k] = v);
    return map;
}

function createPageHash(map: QueryMap): string {
    const q = Object.keys(map)
        .filter(key => key != "base" && !!map[key])
        .map(key => key + "=" + weakEncode(map[key]))
        .join("&");
    return "/" + map.base + (q.length ? "?" + q : "");
}

function weakEncode(s: string | number | boolean) {
    if (!s || typeof s != "string") return s;
    return s; //TODO
}

function setPageHashString(hash: string) {
    if (hash.startsWith("#")) hash = hash.substr(1);
    if (history.pushState) history.pushState(null, null, "#" + hash);
    else document.location.hash = hash;
}