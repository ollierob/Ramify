import {Predicate} from "./Functions";

export function distinct(strings: string[]): string[] {
    return Array.from(new Set(strings));
}

export function moveToFront<T>(elements: T[], match: Predicate<T>): void {
    switch (elements.length) {
        case 0:
        case 1:
            return;
        case 2:
            if (match(elements[0])) return;
            if (!match(elements[1])) return;
            const last = elements[1];
            elements[1] = elements[0];
            elements[0] = last;
            return;
        default:
            const index = elements.findIndex(match);
            if (index <= 0) return;
            const first = elements.splice(index, 1)[0];
            elements.unshift(first);
    }
}