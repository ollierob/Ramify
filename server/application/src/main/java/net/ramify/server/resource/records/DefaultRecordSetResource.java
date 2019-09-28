package net.ramify.server.resource.records;

import net.ramify.model.date.DateRange;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.provider.PlaceProvider;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.collection.RecordSetHierarchy;
import net.ramify.model.record.collection.RecordSetId;
import net.ramify.model.record.collection.RecordSetRelatives;
import net.ramify.model.record.collection.RecordSets;
import net.ramify.model.record.provider.RecordSetProvider;
import net.ramify.model.record.provider.RecordSetRelativesProvider;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.function.Predicate;

@Singleton
public class DefaultRecordSetResource implements RecordSetResource {

    private final RecordSetProvider recordSets;
    private final RecordSetRelativesProvider relatives;
    private final PlaceProvider places;

    @Inject
    DefaultRecordSetResource(
            final RecordSetProvider recordSets,
            final RecordSetRelativesProvider relatives,
            final PlaceProvider places) {
        this.recordSets = recordSets;
        this.relatives = relatives;
        this.places = places;
    }

    @Nonnull
    @Override
    public RecordSets recordSets(
            @CheckForNull final String name,
            @CheckForNull final PlaceId withinPlace,
            @CheckForNull final DateRange withinDate,
            final boolean onlyParents,
            final int limit) {
        Predicate<RecordSet> predicate = r -> true;
        if (name != null) {
            final var l = name.toLowerCase();
            predicate = r -> r.title().toLowerCase().contains(l);
        }
        if (withinPlace != null) {
            final var place = places.require(withinPlace);
            predicate = predicate.and(r -> place.isParentOf(r.resolvePlace(places)));
        }
        if (withinDate != null) {
            predicate = predicate.and(r -> r.date().intersects(withinDate));
        }
        return RecordSets.of(recordSets.matching(predicate, limit, onlyParents));
    }

    @CheckForNull
    @Override
    public RecordSet recordSet(final RecordSetId id) {
        return recordSets.get(id);
    }

    @Override
    public RecordSetRelatives relatives(final RecordSetId id) {
        return relatives.get(id);
    }

    @CheckForNull
    @Override
    public RecordSetHierarchy hierarchy(final RecordSetId id) {
        return relatives.hierarchy(id);
    }

}
