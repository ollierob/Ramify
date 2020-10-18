package net.ramify.model.place.xml;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.SetMultimap;
import net.meerkat.collections.Sets;
import net.meerkat.functions.consumers.Consumers;
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

    private final SetMultimap<PlaceId, ParentChildHierarchy> childHierarchies;
    private final SetMultimap<PlaceId, ParentChildHierarchy> parentHierarchies;

    XmlPlaceHierarchyProvider() {
        this(Maps.newHashMap(), HashMultimap.create(), HashMultimap.create());
    }

    XmlPlaceHierarchyProvider(
            final Map<PlaceHierarchyId, PlaceHierarchy> map,
            final SetMultimap<PlaceId, ParentChildHierarchy> childHierarchies,
            final SetMultimap<PlaceId, ParentChildHierarchy> parentHierarchies) {
        super(map);
        this.childHierarchies = childHierarchies;
        this.parentHierarchies = parentHierarchies;
    }

    PlaceHierarchyProvider immutable() {
        return new XmlPlaceHierarchyProvider(
                this.immutableMap(),
                ImmutableSetMultimap.copyOf(childHierarchies),
                ImmutableSetMultimap.copyOf(parentHierarchies));
    }

    void addAll(final PlaceParserContext context, final Collection<XmlPlace> places) {
        for (final var place : places) {
            final var id = place.placeId(context);
            place.placeHierarchies(id, context).forEach(this::add);
        }
    }

    void add(final ParentChildHierarchy hierarchy) {
        super.put(hierarchy.id(), hierarchy);
        childHierarchies.put(hierarchy.childId(), hierarchy);
        Consumers.ifNonNull(hierarchy.parentId(), id -> parentHierarchies.put(id, hierarchy));
    }

    @Nonnull
    @Override
    public Set<PlaceHierarchy> hierarchiesAbove(final PlaceId placeId) {
        final var parentChild = childHierarchies.get(placeId);
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
        final var parents = childHierarchies.get(parent);
        return switch (parents.size()) {
            case 0 -> Collections.singleton(base);
            case 1 -> Sets.lazilyTransform(this.buildHierarchies(parents.iterator().next()), h -> new ChildParentHierarchy(base.placeId(), h));
            default -> throw new UnsupportedOperationException(); //TODO
        };
    }

    @Nonnull
    @Override
    public Set<ParentChildHierarchy> hierarchiesBelow(final PlaceId placeId, final int depth) {
        if (depth == 0) return Collections.emptySet();
        final var hierarchies = parentHierarchies.get(placeId);
        if (hierarchies.isEmpty() || depth == 1) return hierarchies;
        final var out = new HashSet<>(hierarchies);
        for (final var hierarchy : hierarchies) {
            out.addAll(this.hierarchiesBelow(hierarchy.parentId(), depth - 1));
        }
        return out;
    }

}
