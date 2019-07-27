package net.ramify.server.resource.records;

import net.ramify.model.record.collection.AggregateRecords;
import net.ramify.model.record.collection.IndividualRecords;
import net.ramify.model.record.collection.RecordSetId;
import net.ramify.model.record.collection.Records;
import net.ramify.model.record.image.RecordImages;
import net.ramify.model.record.proto.RecordProto;
import net.ramify.model.record.provider.RecordImageProvider;
import net.ramify.model.record.provider.RecordsProvider;
import net.ramify.utils.collections.ListUtils;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.stream.Collectors;

@Singleton
public class DefaultRecordsResource implements RecordsResource {

    private static final int DEFAULT_LIMIT = 1000;

    private final RecordsProvider records;
    private final RecordSetResource recordSets;
    private final RecordImageProvider imageProvider;
    private final RecordSearch search;

    @Inject
    DefaultRecordsResource(
            final RecordSetResource recordSets,
            final RecordsProvider records,
            final RecordImageProvider imageProvider,
            final RecordSearch search) {
        this.recordSets = recordSets;
        this.records = records;
        this.imageProvider = imageProvider;
        this.search = search;
    }

    @Override
    public RecordSetResource recordSets() {
        return recordSets;
    }

    @Override
    public IndividualRecords in(final RecordSetId id, final boolean includeChildren, final int start, final int limit) {
        final var records = includeChildren ? this.parentAndChildRecords(id) : this.records.require(id);
        return records.individualRecords().paginate(start, limit);
    }

    private Records parentAndChildRecords(final RecordSetId id) {
        final var parent = this.records.require(id);
        final var children = this.recordSets().relatives(id).childIds().map(records::require).collect(Collectors.toList());
        return new AggregateRecords(ListUtils.prefix(parent, children));
    }

    private Records allRecords() {
        return new AggregateRecords(records.all());
    }

    @Override
    public IndividualRecords search(final RecordProto.RecordSearch searchParameters) {
        final var recordSetId = searchParameters.getRecordSetId();
        final var records = recordSetId.isEmpty()
                ? this.allRecords()
                : this.parentAndChildRecords(new RecordSetId(recordSetId));
        return search.search(records, searchParameters);
    }

    @Override
    public RecordImages images(final RecordSetId id) {
        return imageProvider.get(id);
    }

}
