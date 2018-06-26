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
    private final DateRange date;

    public AgeDetails(final Person person, final AgeRange age, final DateRange date) {
        this.person = person;
        this.age = age;
        this.date = date;
    }

    @Nonnull
    @Override
    public Person person() {
        return person;
    }

    public AgeRange age() {
        return age;
    }

    public DateRange date() {
        return date;
    }

    @CheckForNull
    protected Birth birth(final DateRange date) {
        return age == null
                ? null
                : new Birth(date.minus(age.max(), age.min()), "Inferred birth of " + person, Address.UNKNOWN);
    }

    @Nonnull
    @Override
    public Set<Event> inferredEventSet() {
        final Set<Event> events = new HashSet<>();
        events.add(this.birth(date));
        events.remove(null);
        return events;
    }
}
