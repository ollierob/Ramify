import {placeTypeName} from "./PlaceType";

test("placeTypeName", () => {

    expect(placeTypeName(0, false)).toEqual("Country");
    expect(placeTypeName(0, true)).toEqual("Countries");

})