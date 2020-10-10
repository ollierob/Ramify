package net.ramify.model.record.collection;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import net.ramify.data.proto.BuildsProto;
import net.ramify.model.record.proto.RecordProto;

import javax.annotation.Nonnull;
import java.util.Set;
import java.util.stream.Stream;

public interface RecordSets extends BuildsProto<RecordProto.RecordSetList> {

    @Nonnull
    Set<RecordSet> recordSets();

    default Stream<RecordSetId> recordSetIds() {
        return this.recordSets().stream().map(HasRecordSetId::recordSetId);
    }

    @Nonnull
    @Override
    default RecordProto.RecordSetList toProto() {
        return RecordProto.RecordSetList.newBuilder()
                .addAllRecordSet(Iterables.transform(this.recordSets(), RecordSet::toProto))
                .build();
    }

    static RecordSets of(final Set<RecordSet> records) {
        final var immutable = ImmutableSet.copyOf(records);
        return () -> immutable;
    }

}
