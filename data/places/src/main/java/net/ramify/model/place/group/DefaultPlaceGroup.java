package net.ramify.model.place.group;

import net.ramify.model.place.PlaceGroup;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.PlaceId;
import net.ramify.utils.collections.SetUtils;

import javax.annotation.Nonnull;
import java.util.Set;

public class DefaultPlaceGroup implements PlaceGroup {

    private final PlaceGroupId id;
    private final String name;
    private final PlaceId defaultChildId;
    private final Set<PlaceId> childIds;

    public DefaultPlaceGroup(final PlaceGroupId id, final String name, final PlaceId defaultChildId, final Set<PlaceId> childIds) {
        this.id = id;
        this.name = name;
        this.defaultChildId = defaultChildId;
        this.childIds = SetUtils.with(childIds, defaultChildId);
    }

    @Nonnull
    @Override
    public PlaceGroupId id() {
        return id;
    }

    @Nonnull
    @Override
    public String name() {
        return name;
    }

    @Nonnull
    @Override
    public PlaceId defaultChildId() {
        return defaultChildId;
    }

    @Nonnull
    @Override
    public Set<PlaceId> childIds() {
        return childIds;
    }

}
