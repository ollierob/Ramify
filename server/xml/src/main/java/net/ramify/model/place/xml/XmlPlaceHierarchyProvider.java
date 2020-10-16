package net.ramify.model.place.xml;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.SetMultimap;
import net.meerkat.collections.Sets;
import net.ramify.model.AbstractMappedProvider;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.hierarchy.ChildParentHierarchy;
import net.ramify.model.place.hierarchy.ParentChildHierarchy;
import net.ramify.model.place.hierarchy.PlaceHierarchy;
import net.ramify.model.place.hierarchy.PlaceHierarchyId;
import net.ramify.model.place.provider.PlaceHierarchyProvider;
import net.ramify.model.place.xml.place.XmlPlace;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class XmlPlaceHierarchyProvider extends AbstractMappedProvider<PlaceHierarchyId, PlaceHierarchy> implements PlaceHierarchyProvider {

    private final SetMultimap<PlaceId, ParentChildHierarchy> hierarchies;

    XmlPlaceHierarchyProvider() {
        this(Maps.newHashMap(), HashMultimap.create());
    }

    XmlPlaceHierarchyProvider(final Map<PlaceHierarchyId, PlaceHierarchy> map, final SetMultimap<PlaceId, ParentChildHierarchy> hierarchies) {
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
            place.placeHierarchies(id, context).forEach(this::add);
        }
    }

    void add(final ParentChildHierarchy hierarchy) {
        hierarchies.put(hierarchy.childId(), hierarchy);
    }

    @Nonnull
    @Override
    public Set<PlaceHierarchy> hierarchies(final PlaceId placeId) {
        final var parentChild = hierarchies.get(placeId);
        if (parentChild.isEmpty()) return Collections.emptySet();
        final var hierarchies = new HashSet<PlaceHierarchy>();
        for (final var base : parentChild) {
            hierarchies.addAll(this.buildHierarchies(base));
        }
        return hierarchies;
    }

    private Set<PlaceHierarchy> buildHierarchies(final ParentChildHierarchy base) {
        final var parent = base.parentId();
        if (parent == null) return Collections.singleton(base);
        final var parents = hierarchies.get(parent);
        return switch (parents.size()) {
            case 0 -> Collections.singleton(base);
            case 1 -> Sets.lazilyTransform(this.buildHierarchies(parents.iterator().next()), h -> new ChildParentHierarchy(base.placeId(), h));
            default -> throw new UnsupportedOperationException(); //TODO
        };
    }

    private Set<PlaceId> directParentIds(final PlaceId child) {
        return Sets.lazilyTransform(hierarchies.get(child), ParentChildHierarchy::parentId);
    }

}
