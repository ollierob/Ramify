package net.ramify.server.resource.records;

import net.ramify.model.record.collection.RecordSetId;
import net.ramify.model.record.image.RecordImages;
import net.ramify.model.record.provider.RecordImageProvider;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DefaultRecordsResource implements RecordsResource {

    private final RecordSetResource recordSets;
    private final IndividualRecordResource individuals;
    private final RecordImageProvider imageProvider;

    @Inject
    DefaultRecordsResource(
            final RecordSetResource recordSets,
            final IndividualRecordResource individuals,
            final RecordImageProvider imageProvider) {
        this.recordSets = recordSets;
        this.individuals = individuals;
        this.imageProvider = imageProvider;
    }

    @Override
    public RecordSetResource recordSets() {
        return recordSets;
    }

    @Override
    public IndividualRecordResource individuals() {
        return individuals;
    }

    @Override
    public RecordImages images(final RecordSetId id) {
        return imageProvider.get(id);
    }

}
