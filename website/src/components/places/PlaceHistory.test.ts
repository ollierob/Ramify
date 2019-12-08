import {Place} from "../../protobuf/generated/place_pb";
import {addNewPlace} from "./PlaceHistory";

test("addNewPlace", () => {

    const placeA: Place.AsObject = {id: "a", name: "Place A", iso: null, type: null, defunct: false, groupid: null, ismajor: false};
    const placeB: Place.AsObject = {id: "b", name: "Place B", iso: null, type: null, defunct: false, groupid: null, ismajor: false};

    const history = [placeB, placeA];

    const newHistory = addNewPlace(placeA, history);
    expect(newHistory).toEqual([placeA, placeB]);

});