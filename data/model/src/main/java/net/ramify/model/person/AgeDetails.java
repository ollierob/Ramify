package net.ramify.model.person;

import net.ramify.model.event.DateRange;
import net.ramify.model.event.Event;
import net.ramify.model.person.age.AgeRange;
import net.ramify.model.person.event.Birth;
import net.ramify.model.place.address.Address;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;

public class AgeDetails implements PersonalDetails {

    private final Person person;
    private final AgeRange age;

    public AgeDetails(Person person, AgeRange age) {
        this.person = person;
        this.age = age;
    }

    @Nonnull
    @Override
    public Person person() {
        return person;
    }

    public AgeRange age() {
        return age;
    }

    @CheckForNull
    protected Birth birth(final DateRange date) {
        return age == null
                ? null
                : new Birth(date.minus(age.max(), age.min()), "Inferred birth of " + person, Address.UNKNOWN);
    }

    @Nonnull
    @Override
    public Set<Event> inferredEventSet(final DateRange date) {
        final Set<Event> events = new HashSet<>();
        events.add(this.birth(date));
        events.remove(null);
        return events;
    }
}
