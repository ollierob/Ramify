package net.ramify.model.place.xml;

import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.SetMultimap;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.hierarchy.PlaceHierarchy;
import net.ramify.model.place.hierarchy.PlaceHierarchyId;
import net.ramify.model.place.provider.PlaceHierarchyProvider;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.util.Set;

public class XmlPlaceHierarchyProvider implements PlaceHierarchyProvider {

    private final SetMultimap<PlaceId, PlaceId> children;

    public XmlPlaceHierarchyProvider(final SetMultimap<PlaceId, PlaceId> children) {
        this.children = children;
    }

    @Nonnull
    @Override
    public Set<PlaceHierarchy> hierarchies(final PlaceId placeId) {
        throw new UnsupportedOperationException(); //TODO
    }

    @Nonnull
    @Override
    public Set<PlaceId> findByName(final String name, int limit, PlaceId within) {
        throw new UnsupportedOperationException(); //TODO
    }

    @CheckForNull
    @Override
    public PlaceHierarchy get(@Nonnull PlaceHierarchyId key) {
        throw new UnsupportedOperationException(); //TODO
    }

    PlaceHierarchyProvider immutable() {
        return new XmlPlaceHierarchyProvider(ImmutableSetMultimap.copyOf(children));
    }

}
