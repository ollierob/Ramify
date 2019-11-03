package net.ramify.model.record.collection;

import com.google.common.collect.ImmutableList;
import net.ramify.data.proto.BuildsProto;
import net.ramify.model.record.Record;
import net.ramify.model.record.proto.RecordProto;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface Records extends BuildsProto<RecordProto.RecordList> {

    @Nonnull
    Stream<? extends Record> records();

    @Nonnull
    @Override
    default RecordProto.RecordList toProto() {
        return RecordProto.RecordList.newBuilder()
                .addAllRecord(this.records().map(Record::toProto).collect(Collectors.toList()))
                .build();
    }

    @CheckReturnValue
    default Records filter(final Predicate<? super Record> predicate) {
        return () -> this.records().filter(predicate);
    }

    @Nonnull
    default IndividualRecords individualRecords() {
        return () -> this.records().flatMap(Record::individualRecords);
    }

    default Records paginate(final int start, final int limit) {
        return () -> this.records().skip(start).limit(limit);
    }

    static Records of(final Collection<Record> records) {
        final var immutable = ImmutableList.copyOf(records);
        return immutable::stream;
    }

}
