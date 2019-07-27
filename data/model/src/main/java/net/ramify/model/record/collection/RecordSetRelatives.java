package net.ramify.model.record.collection;

import net.ramify.model.record.provider.RecordSetProvider;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.util.Optional;

public interface RecordSetRelatives {

    @CheckForNull
    RecordSetId parent();

    @CheckForNull
    RecordSetId next();

    @CheckForNull
    RecordSetId previous();

    @Nonnull
    default Optional<RecordSet> parent(final RecordSetProvider provider) {
        return provider.maybeGet(this.parent());
    }

    RecordSetRelatives NONE = new RecordSetRelatives() {

        @CheckForNull
        @Override
        public RecordSetId parent() {
            return null;
        }

        @CheckForNull
        @Override
        public RecordSetId next() {
            return null;
        }

        @CheckForNull
        @Override
        public RecordSetId previous() {
            return null;
        }

    };

}
