package net.ramify.model.person;

import net.ramify.model.event.DateRange;
import net.ramify.model.event.Event;
import net.ramify.model.person.age.Age;
import net.ramify.model.person.event.Birth;
import net.ramify.model.person.event.Death;
import net.ramify.model.person.event.Occupation;
import net.ramify.model.place.address.Address;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class AgeAndOccupation implements PersonalDetails {

    private final Person person;
    private final Age age;
    private final String occupation;
    private final boolean deceased;

    public AgeAndOccupation(final Person person, final Age age, final String occupation, final boolean deceased) {
        this.person = person;
        this.age = age;
        this.occupation = occupation;
        this.deceased = deceased;
    }

    @Nonnull
    @Override
    public Person person() {
        return person;
    }

    @Nonnull
    public Optional<Age> age() {
        return Optional.ofNullable(age);
    }

    @Nonnull
    public Optional<String> occupation() {
        return Optional.ofNullable(occupation);
    }

    public boolean deceased() {
        return deceased;
    }

    public Set<Event> inferredEvents(final DateRange date) {
        final Set<Event> events = new HashSet<>();
        if (age != null) {
            events.add(new Birth(date.minus(age.toPeriod()), "Inferred birth of " + person, Address.UNKNOWN));
        }
        if (deceased) {
            final DateRange deathDate = DateRange.before(date);
            events.add(new Death(deathDate, "Inferred death of " + person, Address.UNKNOWN));
            this.occupation().ifPresent(occupation -> events.add(new Occupation(deathDate, occupation, Address.UNKNOWN)));
        } else {
            this.occupation().ifPresent(occupation -> events.add(new Occupation(date, occupation, Address.UNKNOWN)));
        }
        return events;
    }

}
