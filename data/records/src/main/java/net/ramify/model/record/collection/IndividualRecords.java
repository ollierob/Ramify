package net.ramify.model.record.collection;

import com.google.common.collect.Lists;
import net.ramify.data.proto.BuildsProto;
import net.ramify.model.record.IndividualRecord;
import net.ramify.model.record.proto.RecordProto;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Stream;

public interface IndividualRecords extends BuildsProto<RecordProto.IndividualRecordList> {

    @Nonnull
    Stream<? extends IndividualRecord> records();

    @CheckReturnValue
    default IndividualRecords paginate(final int start, final int limit) {
        return () -> this.records().skip(start).limit(limit);
    }

    @CheckReturnValue
    default IndividualRecords filter(final Predicate<? super IndividualRecord> predicate) {
        return () -> this.records().filter(predicate);
    }

    @Nonnull
    @Override
    default RecordProto.IndividualRecordList toProto() {
        final var builder = RecordProto.IndividualRecordList.newBuilder();
        try (final var stream = this.records()) {
            stream.forEach(r -> builder.addRecord(r.toProto()).putRecordSets(r.recordSetId().value(), r.recordSet().toProto()));
        }
        return builder.build();
    }

    static Collector<IndividualRecord, ?, IndividualRecords> collector() {
        return new Collector<IndividualRecord, List<IndividualRecord>, IndividualRecords>() {

            @Override
            public Supplier<List<IndividualRecord>> supplier() {
                return Lists::newArrayList;
            }

            @Override
            public BiConsumer<List<IndividualRecord>, IndividualRecord> accumulator() {
                return List::add;
            }

            @Override
            public BinaryOperator<List<IndividualRecord>> combiner() {
                return (l1, l2) -> {
                    l1.addAll(l2);
                    return l1;
                };
            }

            @Override
            public Function<List<IndividualRecord>, IndividualRecords> finisher() {
                return list -> list::stream;
            }

            @Override
            public Set<Characteristics> characteristics() {
                return Collections.emptySet();
            }

        };
    }

}
