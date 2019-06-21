package net.ramify.model.record;

import net.ramify.model.Id;

public class RecordId extends Id implements HasRecordId {

    public RecordId(final String value) {
        super(value);
    }

    @Deprecated
    @Override
    public RecordId recordId() {
        return this;
    }

}
