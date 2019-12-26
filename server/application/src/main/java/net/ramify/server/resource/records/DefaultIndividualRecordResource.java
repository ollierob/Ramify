package net.ramify.server.resource.records;

import net.ramify.model.record.collection.AggregateRecords;
import net.ramify.model.record.collection.IndividualRecords;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.collection.RecordSetId;
import net.ramify.model.record.collection.Records;
import net.ramify.model.record.proto.RecordProto;
import net.ramify.model.record.provider.RecordSetRelativesProvider;
import net.ramify.model.record.provider.RecordsProvider;
import net.ramify.utils.collections.ListUtils;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.stream.Collectors;

@Singleton
public class DefaultIndividualRecordResource implements IndividualRecordResource {

    private final RecordsProvider records;
    private final RecordSetRelativesProvider relativesProvider;
    private final RecordSearch search;

    @Inject
    DefaultIndividualRecordResource(
            final RecordsProvider records,
            final RecordSetRelativesProvider relativesProvider,
            final RecordSearch search) {
        this.records = records;
        this.relativesProvider = relativesProvider;
        this.search = search;
    }

    @Override
    public IndividualRecords in(final RecordSetId id, final boolean includeChildren, final int start, final int limit) {
        final var records = includeChildren ? this.parentAndChildRecords(id) : this.records.require(id);
        return records.individualRecords(true).paginate(start, limit);
    }

    @Override
    public IndividualRecords search(final RecordProto.RecordSearch searchParameters) {
        final var recordSetId = searchParameters.getRecordSetId();
        final var records = recordSetId.isEmpty()
                ? this.allRecords()
                : this.parentAndChildRecords(new RecordSetId(recordSetId));
        return search.searchIndividuals(records, searchParameters).paginate(0, 100); //FIXME paginate
    }

    private Records parentAndChildRecords(final RecordSetId id) {
        final var parent = records.require(id);
        final var children = relativesProvider.descendants(id).stream().map(RecordSet::recordSetId).map(records::require).collect(Collectors.toList());
        return new AggregateRecords(ListUtils.prefix(parent, children));
    }

    private Records allRecords() {
        return new AggregateRecords(records.all());
    }

}
