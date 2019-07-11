package net.ramify.server.resource.records;

import net.ramify.model.date.DateRange;
import net.ramify.model.place.PlaceId;
import net.ramify.model.record.collection.RecordSets;
import net.ramify.model.record.provider.RecordSetProvider;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DefaultRecordsResource implements RecordsResource {

    private final RecordSetProvider recordSets;

    @Inject
    DefaultRecordsResource(final RecordSetProvider recordSets) {
        this.recordSets = recordSets;
    }

    @Nonnull
    @Override
    public RecordSets recordSets(final PlaceId withinPlace, DateRange withinDate, int limit) {
        throw new UnsupportedOperationException(); //TODO
    }

}
