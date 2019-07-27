package net.ramify.model.record.provider;

import net.ramify.model.Provider;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.collection.RecordSetId;

import javax.annotation.Nonnull;
import java.util.Set;
import java.util.function.Predicate;

public interface RecordSetProvider extends Provider<RecordSetId, RecordSet> {

    @Nonnull
    @Override
    default RecordSet require(final RecordSetId key) {
        return this.requireOrThrow(key, UnknownRecordSetException::new);
    }

    @Nonnull
    default Set<RecordSet> all(final int limit) {
        return this.matching(r -> true, limit, false);
    }

    @Nonnull
    Set<RecordSet> matching(Predicate<? super RecordSet> predicate, int limit, boolean onlyParents);

    class UnknownRecordSetException extends RuntimeException {

        private final RecordSetId id;

        public UnknownRecordSetException(final RecordSetId id) {
            super("Unknown record set: " + id);
            this.id = id;
        }

    }

}
