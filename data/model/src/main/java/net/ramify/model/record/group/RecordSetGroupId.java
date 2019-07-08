package net.ramify.model.record.group;

import net.ramify.model.Id;

public class RecordSetGroupId extends Id implements HasRecordSetGroupId {

    public RecordSetGroupId(final String value) {
        super(value);
    }

    @Deprecated
    @Override
    public RecordSetGroupId recordSetGroupId() {
        return this;
    }
}
