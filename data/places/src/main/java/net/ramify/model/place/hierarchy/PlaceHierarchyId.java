package net.ramify.model.place.hierarchy;

import net.ramify.model.Id;
import net.ramify.model.place.HasPlaceId;

public class PlaceHierarchyId extends Id {

    static PlaceHierarchyId of(final HasPlaceId parentId, final HasPlaceId childId) {
        return new PlaceHierarchyId((parentId == null ? "" : parentId.placeId().toString()) + ':' + (childId == null ? "" : childId.placeId().toString()));
    }

    public PlaceHierarchyId(final String value) {
        super(value);
    }

}
