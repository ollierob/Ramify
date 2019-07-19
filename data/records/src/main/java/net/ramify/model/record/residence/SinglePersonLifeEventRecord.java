package net.ramify.model.record.residence;

import net.ramify.model.date.DateRange;
import net.ramify.model.family.Family;
import net.ramify.model.family.SinglePersonFamily;
import net.ramify.model.person.Person;
import net.ramify.model.record.RecordId;
import net.ramify.model.record.type.LifeEventRecord;

import javax.annotation.Nonnull;
import java.util.Objects;

public class SinglePersonLifeEventRecord implements LifeEventRecord {

    private final RecordId recordId;
    private final Person person;
    private final DateRange date;

    public SinglePersonLifeEventRecord(
            final RecordId recordId,
            final Person person,
            final DateRange date) {
        this.recordId = Objects.requireNonNull(recordId);
        this.person = Objects.requireNonNull(person);
        this.date = Objects.requireNonNull(date);
    }

    @Nonnull
    @Override
    public RecordId recordId() {
        return recordId;
    }

    @Nonnull
    @Override
    public Family family() {
        return new SinglePersonFamily(person);
    }

    @Nonnull
    @Override
    public DateRange date() {
        return date;
    }

}
