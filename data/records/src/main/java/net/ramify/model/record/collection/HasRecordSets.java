package net.ramify.model.record.collection;

import javax.annotation.Nonnull;
import java.util.Set;

public interface HasRecordSets {

    @Nonnull
    Set<RecordSet> recordSets();

}
