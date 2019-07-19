package net.ramify.model.record.collection;

import net.ramify.model.record.Record;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.stream.Stream;

public class AggregateRecords implements Records {

    private final Collection<Records> records;

    public AggregateRecords(final Collection<Records> records) {
        this.records = records;
    }

    @Nonnull
    @Override
    public Stream<? extends Record> records() {
        return records.stream().flatMap(Records::records);
    }

}
