package net.ramify.model.place.hierarchy;

import net.ramify.model.place.PlaceId;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

public class ChildParentHierarchy implements PlaceHierarchy {

    private final PlaceHierarchyId id;
    private final PlaceId placeId;
    private final PlaceHierarchy parent;

    public ChildParentHierarchy(final PlaceId placeId, final PlaceHierarchy parent) {
        this(PlaceHierarchyId.of(parent, placeId), placeId, parent);
    }

    public ChildParentHierarchy(final PlaceHierarchyId id, final PlaceId placeId, final PlaceHierarchy parent) {
        this.id = id;
        this.placeId = placeId;
        this.parent = parent;
    }

    @Nonnull
    @Override
    public PlaceHierarchyId id() {
        return id;
    }

    @Override
    public PlaceId placeId() {
        return placeId;
    }

    @CheckForNull
    @Override
    public PlaceHierarchy parent() {
        return parent;
    }

}
