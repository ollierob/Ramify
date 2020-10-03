package net.ramify.model.place.xml;

import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.SetMultimap;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.hierarchy.PlaceHierarchy;
import net.ramify.model.place.hierarchy.PlaceHierarchyId;
import net.ramify.model.place.provider.PlaceHierarchyProvider;
import net.ramify.model.place.xml.place.XmlPlace;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Set;

public class XmlPlaceHierarchyProvider implements PlaceHierarchyProvider {

    private final SetMultimap<PlaceId, PlaceId> children;

    public XmlPlaceHierarchyProvider(final SetMultimap<PlaceId, PlaceId> children) {
        this.children = children;
    }

    PlaceHierarchyProvider immutable() {
        return new XmlPlaceHierarchyProvider(ImmutableSetMultimap.copyOf(children));
    }

    void addAll(final PlaceParserContext context, final Collection<XmlPlace> places) {
        //TODO
    }

    @Nonnull
    @Override
    public Set<PlaceHierarchy> hierarchies(final PlaceId placeId) {
        throw new UnsupportedOperationException(); //TODO
    }

    @Nonnull
    @Override
    public Set<PlaceId> findByName(final String name, final int limit, final PlaceId within) {
        throw new UnsupportedOperationException(); //TODO
    }

    @CheckForNull
    @Override
    public PlaceHierarchy get(@Nonnull final PlaceHierarchyId key) {
        throw new UnsupportedOperationException(); //TODO
    }

}
