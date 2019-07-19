package net.ramify.server.resource.records;

import net.ramify.model.record.collection.AggregateRecords;
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

    private final RecordsProvider records;
    private final RecordSetResource recordSets;
    private final RecordImageProvider imageProvider;

    @Inject
    DefaultRecordsResource(final RecordSetResource recordSets, final RecordsProvider records, final RecordImageProvider imageProvider) {
        this.recordSets = recordSets;
        this.records = records;
        this.imageProvider = imageProvider;
    }

    @Override
    public RecordSetResource recordSets() {
        return recordSets;
    }

    @Override
    public Records in(final RecordSetId id, final boolean includeChildren, final int start, final int limit) {
        final var records = includeChildren ? this.parentAndChildRecords(id) : this.records.require(id);
        return records.paginate(start, limit);
    }

    private Records parentAndChildRecords(final RecordSetId id) {
        final var parent = this.records.require(id);
        final var children = this.recordSets().children(id).recordSetIds().map(this.records::require).collect(Collectors.toList());
        return new AggregateRecords(ListUtils.prefix(parent, children));
    }

    @Override
    public Records search(final RecordProto.RecordSearch searchParameters) {
        throw new UnsupportedOperationException(); //TODO
    }

    @Override
    public RecordImages images(final RecordSetId id) {
        return imageProvider.get(id);
    }

}
