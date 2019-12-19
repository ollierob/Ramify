import {moveToFront} from "./Arrays";

test("moveToFront", () => {

    const array = [1, 2, 3, 4, 5];
    moveToFront(array, i => i == 3);

    expect(array).toEqual([3, 1, 2, 4, 5]);

});