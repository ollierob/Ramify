package net.ramify.model.record.census.uk;

import net.ramify.model.event.DateRange;
import net.ramify.model.family.Family;
import net.ramify.model.person.Person;
import net.ramify.model.person.event.PersonalEvents;
import net.ramify.model.place.address.Address;
import net.ramify.model.record.census.AbstractCensusRecord;

import javax.annotation.Nonnull;
import java.util.Map;

public class UkEnumeratedCensusRecord extends AbstractCensusRecord implements UkCensusRecord {

    protected UkEnumeratedCensusRecord(DateRange date, Address address) {
        super(date, address);
    }

    @Nonnull
    @Override
    public Map<Person, PersonalEvents> personalEvents() {
        throw new UnsupportedOperationException(); //TODO
    }

    @Nonnull
    @Override
    public Family family() {
        throw new UnsupportedOperationException(); //TODO
    }
}
