package net.ramify.model.person;

import net.ramify.model.event.DateRange;
import net.ramify.model.event.Event;
import net.ramify.model.person.age.AgeRange;
import net.ramify.model.person.event.Death;
import net.ramify.model.person.event.Occupation;
import net.ramify.model.place.address.Address;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.Set;

public class AgeAndOccupationDetails extends AgeDetails {

    private final String occupation;
    private final boolean deceased;

    public AgeAndOccupationDetails(
            final Person person,
            final AgeRange age,
            final String occupation,
            final DateRange date) {
        this(person, age, occupation, date, false);
    }

    public AgeAndOccupationDetails(
            final Person person,
            final AgeRange age,
            final String occupation,
            final DateRange date,
            final boolean deceased) {
        super(person, age, date);
        this.occupation = occupation;
        this.deceased = deceased;
    }

    @Nonnull
    public Optional<String> occupation() {
        return Optional.ofNullable(occupation);
    }

    public boolean deceased() {
        return deceased;
    }

    @Override
    public Set<Event> inferredEventSet() {
        final Set<Event> events = super.inferredEventSet();
        if (deceased) {
            final DateRange deathDate = DateRange.before(this.date());
            events.add(new Death(deathDate, "Inferred death of " + this.person(), Address.UNKNOWN));
            this.occupation().ifPresent(occupation -> events.add(new Occupation(deathDate, occupation, Address.UNKNOWN)));
        } else {
            this.occupation().ifPresent(occupation -> events.add(new Occupation(this.date(), occupation, Address.UNKNOWN)));
        }
        return events;
    }

}
