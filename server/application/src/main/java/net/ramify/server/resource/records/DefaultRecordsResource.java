package net.ramify.server.resource.records;

import net.ramify.model.record.collection.RecordSetId;
import net.ramify.model.record.collection.Records;
import net.ramify.model.record.proto.RecordProto;
import net.ramify.model.record.provider.RecordsProvider;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DefaultRecordsResource implements RecordsResource {

    private final RecordSetResource recordSets;
    private final RecordsProvider records;

    @Inject
    DefaultRecordsResource(final RecordSetResource recordSets, final RecordsProvider records) {
        this.recordSets = recordSets;
        this.records = records;
    }

    @Override
    public RecordSetResource recordSets() {
        return recordSets;
    }

    @Override
    public Records in(final RecordSetId id, final int start, final int limit) {
        //TODO pagination
        return records.get(id).paginate(start, limit);
    }

    @Override
    public Records search(final RecordProto.RecordSearch searchParameters) {
        throw new UnsupportedOperationException(); //TODO
    }
}
