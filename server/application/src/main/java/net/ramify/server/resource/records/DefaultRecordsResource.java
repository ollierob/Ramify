package net.ramify.server.resource.records;

import net.ramify.model.record.collection.Records;
import net.ramify.model.record.proto.RecordProto;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DefaultRecordsResource implements RecordsResource {

    private final RecordSetResource recordSets;

    @Inject
    DefaultRecordsResource(final RecordSetResource recordSets) {
        this.recordSets = recordSets;
    }

    @Override
    public RecordSetResource recordSets() {
        return recordSets;
    }

    @Override
    public Records search(final RecordProto.RecordSearch searchParameters) {
        throw new UnsupportedOperationException(); //TODO
    }
}
