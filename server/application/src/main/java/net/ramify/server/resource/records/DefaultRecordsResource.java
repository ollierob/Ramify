package net.ramify.server.resource.records;

import net.ramify.model.record.collection.AggregateRecords;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.collection.RecordSetId;
import net.ramify.model.record.collection.Records;
import net.ramify.model.record.image.RecordImages;
import net.ramify.model.record.provider.RecordImageProvider;
import net.ramify.model.record.provider.RecordSetRelativesProvider;
import net.ramify.model.record.provider.RecordsProvider;
import net.ramify.utils.collections.ListUtils;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.stream.Collectors;

@Singleton
public class DefaultRecordsResource implements RecordsResource {

    private final RecordsProvider records;
    private final RecordSetRelativesProvider relatives;
    private final RecordSetResource recordSets;
    private final IndividualRecordResource individuals;
    private final RecordImageProvider imageProvider;

    @Inject
    DefaultRecordsResource(
            final RecordsProvider records,
            final RecordSetRelativesProvider relatives,
            final RecordSetResource recordSets,
            final IndividualRecordResource individuals,
            final RecordImageProvider imageProvider) {
        this.records = records;
        this.relatives = relatives;
        this.recordSets = recordSets;
        this.individuals = individuals;
        this.imageProvider = imageProvider;
    }

    @Override
    public Records records(
            final RecordSetId id,
            final boolean includeChildren,
            final int start,
            final int limit) {
        final var records = includeChildren ? this.parentAndChildRecords(id) : this.records.require(id);
        return records.paginate(start, limit);
    }

    private Records parentAndChildRecords(final RecordSetId id) {
        final var parent = records.require(id);
        final var children = relatives.descendants(id).stream().map(RecordSet::recordSetId).map(records::require).collect(Collectors.toList());
        return new AggregateRecords(ListUtils.prefix(parent, children));
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
