package net.ramify.server.resource.records;

import net.ramify.model.date.ClosedDateRange;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.provider.PlaceProvider;
import net.ramify.model.record.RecordId;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.collection.RecordSetHierarchy;
import net.ramify.model.record.collection.RecordSetId;
import net.ramify.model.record.collection.RecordSetRelatives;
import net.ramify.model.record.collection.RecordSets;
import net.ramify.model.record.provider.RecordProvider;
import net.ramify.model.record.provider.RecordSetProvider;
import net.ramify.model.record.provider.RecordSetRelativesProvider;
import net.ramify.utils.objects.Functions;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.LocalDate;
import java.util.function.Predicate;

@Singleton
public class DefaultRecordSetResource implements RecordSetResource {

    private final RecordProvider records;
    private final RecordSetProvider recordSets;
    private final RecordSetRelativesProvider relatives;
    private final PlaceProvider places;

    @Inject
    DefaultRecordSetResource(
            final RecordProvider records,
            final RecordSetProvider recordSets,
            final RecordSetRelativesProvider relatives,
            final PlaceProvider places) {
        this.records = records;
        this.recordSets = recordSets;
        this.relatives = relatives;
        this.places = places;
    }

    @Nonnull
    @Override
    public RecordSets recordSets(
            @CheckForNull final String name,
            @CheckForNull final PlaceId withinPlace,
            final LocalDate fromDate,
            final LocalDate toDate,
            final boolean onlyParents,
            final int limit) {
        Predicate<RecordSet> predicate = r -> true;
        if (name != null) {
            final var l = name.toLowerCase();
            predicate = r -> r.title().toLowerCase().contains(l);
        }
        if (withinPlace != null) {
            final var place = places.require(withinPlace);
            predicate = predicate.and(record -> this.isCovered(record, place));
        }
        final var dateRange = fromDate == null && toDate == null ? null : ClosedDateRange.of(fromDate, toDate);
        if (dateRange != null) {
            predicate = predicate.and(r -> dateRange.intersects(r.date()));
        }
        return RecordSets.of(recordSets.matching(predicate, limit, onlyParents));
    }

    private boolean isCovered(final RecordSet recordSet, final Place place) {
        if (place.isParentOf(recordSet.covers().resolvePlace(places))) return true;
        final var createdPlace = Functions.ifNonNull(recordSet.createdBy(), id -> id.resolvePlace(places));
        return createdPlace != null && place.isParentOf(createdPlace);
    }

    @CheckForNull
    @Override
    public RecordSet recordSet(final RecordSetId id) {
        return recordSets.get(id);
    }

    @Override
    public RecordSetId recordSetId(final RecordId id) {
        return records.getRecordSetId(id);
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
