package net.ramify.model.record.collection;

import javax.annotation.Nonnull;

public interface HasRecordSet extends HasRecordSetId{

    @Nonnull
    RecordSet recordSet();

    @Nonnull
    @Override
    default RecordSetId recordSetId() {
        return this.recordSet().recordSetId();
    }

}
