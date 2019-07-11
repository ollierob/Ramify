package net.ramify.model.record.collection;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import net.ramify.data.proto.BuildsProto;
import net.ramify.model.record.proto.RecordProto;

import javax.annotation.Nonnull;
import java.util.Set;

public interface RecordSets extends BuildsProto<RecordProto.RecordSetList> {

    @Nonnull
    Set<RecordSet> recordSets();

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
