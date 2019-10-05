import {Sorter} from "./Sort";

export type StringMap<T = string> = {[k: string]: T}

export function stringMap<T>(objects: ReadonlyArray<T>, toKey: (t: T) => string): StringMap<T> {
    if (!objects || !objects.length) return {};
    const map: StringMap<T> = {};
    objects.forEach(o => map[toKey(o)] = o);
    return map;
}

export function stringMultimap<T>(objects: ReadonlyArray<T>, toKey: (t: T) => string, sorter?: Sorter<T>): StringMap<T[]> {
    if (!objects || !objects.length) return {};
    const map: StringMap<T[]> = {};
    objects.forEach(o => {
        const k = toKey(o);
        let array = map[k];
        if (!array) array = map[k] = [];
        array.push(o);
    });
    if (sorter) Object.keys(map).forEach(k => map[k].sort(sorter));
    return map;
}

export type NumberMap<T> = {[k: number]: T}

export function numberMap<T>(objects: ReadonlyArray<T>, toNumber: (t: T) => number): NumberMap<T> {
    if (!objects || !objects.length) return {};
    const map: NumberMap<T> = {};
    objects.forEach(o => map[toNumber(o)] = o);
    return map;
}

export function integerKeys(map: NumberMap<any>): number[] {
    return Object.keys(map).map(m => parseInt(m));
}

export function ensure<V>(k: string, map: StringMap<V>, create: (k: string) => V): V {
    let v = map[k];
    if (v == null) map[k] = v = create(k);
    return v;
}