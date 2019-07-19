package net.ramify.model.record.residence;

import net.ramify.model.date.DateRange;
import net.ramify.model.family.Family;
import net.ramify.model.family.SinglePersonFamily;
import net.ramify.model.person.Person;
import net.ramify.model.record.RecordId;
import net.ramify.model.record.type.LifeEventRecord;

import javax.annotation.Nonnull;

public class SinglePersonLifeEventRecord implements LifeEventRecord {

    private final RecordId recordId;
    private final Person person;
    private final DateRange date;

    public SinglePersonLifeEventRecord(
            final RecordId recordId,
            final Person person,
            final DateRange date) {
        this.recordId = recordId;
        this.person = person;
        this.date = date;
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
