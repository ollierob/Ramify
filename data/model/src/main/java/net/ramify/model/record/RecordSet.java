package net.ramify.model.record;

import net.ramify.model.record.set.HasRecordSetId;

import javax.annotation.Nonnull;
import java.util.stream.Stream;

public interface RecordSet extends HasRecordSetId {

    @Nonnull
    Stream<Record> records();

}
