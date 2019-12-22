import {Point} from "../../../protobuf/generated/location_pb";
import {GeoJSON} from "react-leaflet";

export function toGeoJson(points: ReadonlyArray<Point.AsObject>): GeoJSON.GeoJsonObject {
    if (!points) return null;
    switch (points.length) {
        case 0:
        case 2:
            return null;
        case 1:
            return toGeoJsonPoint(points[0]);
        default:
            return toGeoJsonArea(points);
    }
}

function toGeoJsonPoint(point: Point.AsObject): GeoJSON.Point {
    return {
        type: "Point",
        coordinates: toGeoJsonPosition(point)
    };
}

function toGeoJsonArea(points: ReadonlyArray<Point.AsObject>): GeoJSON.Feature {
    return {
        type: "Feature",
        properties: {},
        geometry: {
            type: "Polygon",
            coordinates: [points.map(toGeoJsonPosition)]
        }
    };
}

function toGeoJsonPosition(point: Point.AsObject): GeoJSON.Position {
    return [point.longitude, point.latitude];
}