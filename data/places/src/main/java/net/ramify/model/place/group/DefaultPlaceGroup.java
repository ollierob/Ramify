package net.ramify.model.place.group;

import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroup;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.utils.collections.SetUtils;

import javax.annotation.Nonnull;
import java.util.Set;

public class DefaultPlaceGroup implements PlaceGroup {

    private final PlaceGroupId id;
    private final String name;
    private final Place defaultChild;
    private final Set<Place> children;

    public DefaultPlaceGroup(final PlaceGroupId id, final String name, final Place defaultChild, final Set<Place> children) {
        this.id = id;
        this.name = name;
        this.defaultChild = defaultChild;
        this.children = SetUtils.with(children, defaultChild);
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
    public Place defaultChild() {
        return defaultChild;
    }

    @Nonnull
    @Override
    public Set<Place> children() {
        return children;
    }

}
