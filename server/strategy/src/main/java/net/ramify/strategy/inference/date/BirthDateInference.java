package net.ramify.strategy.inference.date;

import net.ramify.model.date.DateRange;
import net.ramify.model.event.collection.PersonEventSet;
import net.ramify.model.event.type.BirthEvent;

import javax.annotation.Nonnull;
import javax.inject.Singleton;
import java.util.Optional;

@Singleton
public class BirthDateInference implements DateInference<PersonEventSet> {

    @Nonnull
    @Override
    public Optional<DateRange> infer(final PersonEventSet events) {

        final var birth = events.findBirth();
        if (birth.isPresent() && birth.get().date().isExact()) return birth.map(BirthEvent::date);

        DateRange birthDate = null;

        for (final var event : events) {

            //Ignore age=0 on birth, or any age post-death
            if (event.isBirth() || event.isPostDeath()) continue;

            final var givenAge = event.givenAge().orElse(null);
            if (givenAge == null) continue;

            final var eventBirthDate = givenAge.birthDate(event.date());
            if (birthDate == null) {
                birthDate = eventBirthDate;
            } else {
                final var intersection = birthDate.intersection(eventBirthDate);
                if (intersection.isEmpty()) return Optional.empty();
                birthDate = intersection.get();
            }

        }

        return Optional.ofNullable(birthDate);

    }

}
