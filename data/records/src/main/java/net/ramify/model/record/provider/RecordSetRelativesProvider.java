package net.ramify.model.record.provider;

import com.google.common.collect.Sets;
import net.ramify.model.util.provider.Provider;
import net.ramify.model.record.collection.HasRecordSetId;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.collection.RecordSetHierarchy;
import net.ramify.model.record.collection.RecordSetId;
import net.ramify.model.record.collection.RecordSetRelatives;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

public interface RecordSetRelativesProvider extends Provider<RecordSetId, RecordSetRelatives> {

    default Optional<RecordSetRelatives> maybeGet(final HasRecordSetId id) {
        return this.maybeGet(id.recordSetId());
    }

    default RecordSet parentOf(final HasRecordSetId id) {
        return this.maybeGet(id).map(RecordSetRelatives::parent).orElse(null);
    }

    default boolean containsAnyParent(final Set<RecordSetId> ids, final HasRecordSetId child) {
        if (child == null) return false;
        if (ids.contains(child.recordSetId())) return true;
        return this.containsAnyParent(ids, this.parentOf(child));
    }

    @Nonnull
    default Set<RecordSet> descendants(final HasRecordSetId parentId) {
        final var relatives = this.get(parentId.recordSetId());
        if (relatives == null) return Collections.emptySet();
        final var children = relatives.children();
        if (children.isEmpty()) return Collections.emptySet();
        final var set = Sets.<RecordSet>newHashSet();
        children.forEach(child -> {
            set.add(child);
            set.addAll(this.descendants(child));
        });
        return set;
    }

    @Nonnull
    default Set<RecordSet> immediateChildren(@Nonnull final HasRecordSetId id) {
        return this.maybeGet(id).map(RecordSetRelatives::children).orElse(Collections.emptySet());
    }

    @CheckForNull
    default RecordSetHierarchy hierarchy(@Nonnull final HasRecordSetId id) {

        final var relatives = this.get(id.recordSetId());
        if (relatives == null) return null;

        return new RecordSetHierarchy() {

            @Nonnull
            @Override
            public RecordSetRelatives relatives() {
                return relatives;
            }

            @CheckForNull
            @Override
            public RecordSetHierarchy parent() {
                final var parent = relatives.parent();
                return parent == null ? null : RecordSetRelativesProvider.this.hierarchy(parent);
            }

        };
    }

}

