package net.ramify.server.resource.records;

import net.ramify.model.date.DateRange;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.provider.PlaceProvider;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.collection.RecordSetId;
import net.ramify.model.record.collection.RecordSets;
import net.ramify.model.record.provider.RecordSetProvider;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.function.Predicate;

@Singleton
public class DefaultRecordSetResource implements RecordSetResource {

    private final RecordSetProvider recordSets;
    private final PlaceProvider places;

    @Inject
    DefaultRecordSetResource(final RecordSetProvider recordSets, final PlaceProvider places) {
        this.recordSets = recordSets;
        this.places = places;
    }

    @Nonnull
    @Override
    public RecordSets recordSets(
            @CheckForNull final PlaceId withinPlace,
            @CheckForNull final DateRange withinDate,
            final int limit) {
        Predicate<RecordSet> predicate = r -> true;
        if (withinPlace != null) {
            final var place = places.require(withinPlace);
            predicate = r -> place.isParentOf(r.resolvePlace(places));
        }
        if (withinDate != null) {
            predicate = predicate.and(r -> r.date().intersects(withinDate));
        }
        return RecordSets.of(recordSets.matching(predicate, limit));
    }

    @CheckForNull
    @Override
    public RecordSet recordSet(final RecordSetId id) {
        return recordSets.get(id);
    }

    @Override
    public RecordSets children(final RecordSetId parentId) {
        return RecordSets.of(recordSets.children(parentId));
    }

}
