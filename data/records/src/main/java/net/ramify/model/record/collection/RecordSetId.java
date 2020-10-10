package net.ramify.model.record.collection;

import net.ramify.model.Id;

public class RecordSetId extends Id implements HasRecordSetId {

    public RecordSetId(final String value) {
        super(value);
    }

    @Deprecated
    @Override
    public RecordSetId recordSetId() {
        return this;
    }

}
