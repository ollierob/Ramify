export type StringMap<T> = {[k: string]: T}

export function stringMap<T>(objects: ReadonlyArray<T>, toString: (t: T) => string): StringMap<T> {
    if (!objects || !objects.length) return {};
    const map: StringMap<T> = {};
    objects.forEach(o => map[toString(o)] = o);
    return map;
}

export function stringMultimap<T>(objects: ReadonlyArray<T>, toString: (t: T) => string): StringMap<T[]> {
    if (!objects || !objects.length) return {};
    const map: StringMap<T[]> = {};
    objects.forEach(o => {
        const k = toString(o);
        let array = map[k];
        if (!array) array = map[k] = [];
        array.push(o);
    });
    return map;
}