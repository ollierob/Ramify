package net.ramify.model.record.collection;

import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import net.ramify.data.proto.BuildsProto;
import net.ramify.model.place.Place;
import net.ramify.model.place.collection.HasPlaces;
import net.ramify.model.record.Record;
import net.ramify.model.record.RecordId;
import net.ramify.model.record.proto.RecordProto;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface Records extends HasRecordSets, HasPlaces, BuildsProto<RecordProto.RecordList> {

    @Nonnull
    Stream<? extends Record> records();

    default boolean has(final RecordId recordId) {
        return this.records().anyMatch(record -> record.recordId().equals(recordId));
    }

    @Nonnull
    @Override
    default Set<RecordSet> recordSets() {
        final var byId = HashBiMap.<RecordSetId, RecordSet>create();
        this.records().forEach(record -> byId.put(record.recordSetId(), record.recordSet()));
        return byId.values();
    }

    @Nonnull
    default Optional<? extends Record> record(final RecordId id) {
        return this.records()
                .filter(record -> record.recordId().equals(id))
                .findAny();
    }

    @Nonnull
    default Optional<RecordSet> recordSet(final RecordId id) {
        return this.record(id).map(Record::recordSet);
    }

    @Nonnull
    @Override
    default Set<? extends Place> places() {
        return this.records()
                .map(Record::places)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    @CheckReturnValue
    default Records filter(final Predicate<? super Record> predicate) {
        return () -> this.records().filter(predicate);
    }

    @Nonnull
    default IndividualRecords individualRecords(final boolean ignoreUnnamed) {
        return () -> this.records().flatMap(Record::individualRecords).filter(r -> !ignoreUnnamed || !r.person().name().isUnknown());
    }

    default Records paginate(final int start, final int limit) {
        return () -> this.records().skip(start).limit(limit);
    }

    @Nonnull
    @Override
    default RecordProto.RecordList toProto() {
        return RecordProto.RecordList.newBuilder()
                .addAllRecord(this.records().map(Record::toProto).collect(Collectors.toList()))
                .addAllRecordSets(Iterables.transform(this.recordSets(), RecordSet::toProto))
                .build();
    }

    static Records of(final Collection<Record> records) {
        if (records.isEmpty()) return none();
        final var immutable = ImmutableList.copyOf(records);
        return immutable::stream;
    }

    static Records none() {
        return Stream::empty;
    }

}
