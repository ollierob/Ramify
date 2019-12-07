package net.ramify.server.resource.records;

import net.ramify.model.place.PlaceId;
import net.ramify.model.record.collection.IndividualRecords;
import net.ramify.model.record.collection.Records;
import net.ramify.model.record.provider.RecordsProvider;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DefaultPlaceRecordResource implements PlaceRecordResource {

    private final RecordsProvider records;

    @Inject
    DefaultPlaceRecordResource(final RecordsProvider records) {
        this.records = records;
    }

    @Override
    public Records at(final PlaceId placeId, final int start, final int limit) {
        return this.records.placeIndex().getOrDefault(placeId, Records.none()).paginate(start, limit);
    }

    @Override
    public IndividualRecords individuals(final PlaceId placeId) {
        throw new UnsupportedOperationException(); //TODO
    }

}
