package net.ramify.model.record.provider;

import net.ramify.model.Provider;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.collection.RecordSetId;

import javax.annotation.Nonnull;
import java.util.Set;
import java.util.function.Predicate;

public interface RecordSetProvider extends Provider<RecordSetId, RecordSet> {

    @Nonnull
    default Set<RecordSet> all(final int limit) {
        return this.matching(r -> true, limit);
    }

    @Nonnull
    Set<RecordSet> matching(Predicate<? super RecordSet> predicate, int limit);

}
