package net.ramify.model.place.hierarchy;

import net.ramify.model.place.PlaceId;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.util.Objects;

public class ParentChildHierarchy implements PlaceHierarchy {

    private final PlaceId parent;
    private final PlaceId child;

    public ParentChildHierarchy(final PlaceId parent, final PlaceId child) {
        this.parent = parent;
        this.child = child;
    }

    @Nonnull
    @Override
    public PlaceHierarchyId id() {
        return PlaceHierarchyId.of(parent, child);
    }

    @Override
    public PlaceId placeId() {
        return child;
    }

    @CheckForNull
    public PlaceId parentId() {
        return parent;
    }

    @Nonnull
    public PlaceId childId() {
        return child;
    }

    @CheckForNull
    @Override
    public ParentChildHierarchy parent() {
        return parent == null ? null : new ParentChildHierarchy(null, parent);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final var that = (ParentChildHierarchy) o;
        return Objects.equals(parent, that.parent)
                && Objects.equals(child, that.child);
    }

    @Override
    public int hashCode() {
        int result = parent != null ? parent.hashCode() : 0;
        result = 31 * result + child.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return parent + "<-" + child;
    }

}
