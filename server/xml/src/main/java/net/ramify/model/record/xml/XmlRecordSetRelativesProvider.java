package net.ramify.model.record.xml;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.ramify.model.AbstractMappedProvider;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.collection.RecordSetId;
import net.ramify.model.record.collection.RecordSetRelatives;
import net.ramify.model.record.provider.RecordSetProvider;
import net.ramify.model.record.provider.RecordSetRelativesProvider;
import net.ramify.model.record.xml.collection.DefaultRecordSet;
import net.ramify.utils.collections.SetUtils;
import net.ramify.utils.objects.Consumers;
import net.ramify.utils.objects.Functions;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.inject.Singleton;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Map;
import java.util.Set;

@XmlTransient
@Singleton
class XmlRecordSetRelativesProvider extends AbstractMappedProvider<RecordSetId, RecordSetRelatives> implements RecordSetRelativesProvider {

    private final Map<RecordSetId, LazyRelationships> workingSet = Maps.newHashMap();

    XmlRecordSetRelativesProvider(final Map<RecordSetId, RecordSetRelatives> map) {
        super(map);
    }

    void add(final DefaultRecordSet recordSet) {
        final var lazy = workingSet.computeIfAbsent(recordSet.recordSetId(), LazyRelationships::new);
        Consumers.ifNonNull(recordSet.nextId(), nextId -> {
            lazy.setNext(nextId);
            workingSet.computeIfAbsent(nextId, LazyRelationships::new).setPrevious(recordSet.recordSetId());
        });
        Consumers.ifNonNull(recordSet.parentId(), parentId -> {
            lazy.setParent(parentId);
            final var parentLazy = workingSet.computeIfAbsent(parentId, LazyRelationships::new);
            parentLazy.addChild(recordSet.recordSetId());
        });
    }

    RecordSetRelativesProvider immutable(final RecordSetProvider recordSets) {
        workingSet.values().forEach(value -> this.add(value, recordSets));
        return new XmlRecordSetRelativesProvider(this.immutableMap());
    }

    private void add(final LazyRelationships lazy, final RecordSetProvider recordSets) {
        super.put(lazy.id, lazy.build(recordSets));
    }

    private static class LazyRelationships {

        private final RecordSetId id;
        private RecordSetId parentId, nextId, previousId;
        private final Set<RecordSetId> childIds = Sets.newHashSet();

        LazyRelationships(final RecordSetId id) {
            this.id = id;
        }

        void setParent(final RecordSetId id) {
            this.parentId = id;
        }

        void setNext(final RecordSetId id) {
            this.nextId = id;
        }

        void setPrevious(final RecordSetId id) {
            this.previousId = id;
        }

        void addChild(final RecordSetId id) {
            this.childIds.add(id);
        }

        RecordSetRelatives build(final RecordSetProvider recordSets) {
            return new DefaultRecordSetRelatives(
                    Functions.ifNonNull(parentId, recordSets::require),
                    Functions.ifNonNull(nextId, recordSets::require),
                    Functions.ifNonNull(previousId, recordSets::require),
                    ImmutableSet.copyOf(SetUtils.transform(childIds, recordSets::require)));
        }

    }

    @XmlTransient
    private static class DefaultRecordSetRelatives implements RecordSetRelatives {

        private final RecordSet parent, next, previous;
        private final Set<RecordSet> children;

        DefaultRecordSetRelatives(RecordSet recordSet, RecordSet next, RecordSet previous, Set<RecordSet> children) {
            parent = recordSet;
            this.next = next;
            this.previous = previous;
            this.children = children;
        }

        @CheckForNull
        @Override
        public RecordSet parent() {
            return parent;
        }

        @CheckForNull
        @Override
        public RecordSet next() {
            return next;
        }

        @CheckForNull
        @Override
        public RecordSet previous() {
            return previous;
        }

        @Nonnull
        @Override
        public Set<RecordSet> children() {
            return children;
        }

    }

}
