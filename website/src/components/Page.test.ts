import {QueryMap, updatePageHash} from "./Page";
import {StringMap} from "./Maps";

test("updatePageHash", () => {

    const delta: QueryMap = {base: "foo", id: "x"};
    const current: StringMap = {base: "foo", id: "y", name: "waa"};
    let newHash: string = null;
    updatePageHash(delta, current, h => newHash = h);
    expect(newHash).toBe("/foo?id=x&name=waa");

});