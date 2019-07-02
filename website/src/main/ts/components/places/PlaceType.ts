//TODO this should be determined server-side
const TypePrecedence = {

    Country: 0,

    State: 1,
    County: 2,

    Hundred: 3,
    Wapentake: 3,

    Parish: 4,

    Town: 5,
    Township: 5.1,

    Village: 6,
    Hamlet: 6.1,

    Church: 7

};

export function sortByType(t1: string, t2: string): number {
    const p1 = TypePrecedence[t1] || 99;
    const p2 = TypePrecedence[t2] || 99;
    return p1 - p2;
}
