package net.ramify.model.place.xml;

import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.SetMultimap;
import net.ramify.model.AbstractMappedProvider;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.hierarchy.PlaceHierarchy;
import net.ramify.model.place.hierarchy.PlaceHierarchyId;
import net.ramify.model.place.provider.PlaceHierarchyProvider;
import net.ramify.model.place.xml.place.XmlPlace;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class XmlPlaceHierarchyProvider extends AbstractMappedProvider<PlaceHierarchyId, PlaceHierarchy> implements PlaceHierarchyProvider {

    private final SetMultimap<PlaceId, PlaceHierarchy> hierarchies;

    XmlPlaceHierarchyProvider(final Map<PlaceHierarchyId, PlaceHierarchy> map, final SetMultimap<PlaceId, PlaceHierarchy> hierarchies) {
        super(map);
        this.hierarchies = hierarchies;
    }

    PlaceHierarchyProvider immutable() {
        return new XmlPlaceHierarchyProvider(
                this.immutableMap(),
                ImmutableSetMultimap.copyOf(hierarchies));
    }

    void addAll(final PlaceParserContext context, final Collection<XmlPlace> places) {
        for (final var place : places) {
            final var id = place.placeId(context);
        }
    }

    @Nonnull
    @Override
    public Set<PlaceHierarchy> hierarchies(final PlaceId placeId) {
        return hierarchies.get(placeId);
    }

}
