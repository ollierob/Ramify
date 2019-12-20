package net.ramify.strategy.inference.event;

import net.ramify.model.date.DateRange;
import net.ramify.model.event.EventBuilder;
import net.ramify.model.event.collection.PersonEventSet;
import net.ramify.model.event.type.BirthEvent;
import net.ramify.model.person.PersonId;
import net.ramify.strategy.inference.date.BirthDateInference;
import net.ramify.strategy.inference.date.DateInference;

import javax.annotation.Nonnull;
import java.util.Optional;

public class BirthEventInference implements PersonEventInference<BirthEvent> {

    public static Optional<BirthEvent> infer(final PersonId personId, final PersonEventSet events, final BirthDateInference dateInference) {
        return dateInference.infer(events).map(date -> EventBuilder.builderWithRandomId(date).toBirth(personId));
    }

    private final PersonId id;
    private final DateInference<PersonEventSet> dateInference;

    public BirthEventInference(final PersonId id, final DateInference<PersonEventSet> dateInference) {
        this.id = id;
        this.dateInference = dateInference;
    }

    @Nonnull
    @Override
    public Optional<BirthEvent> infer(final PersonEventSet events) {
        return dateInference.infer(events).map(this::infer);
    }

    private BirthEvent infer(@Nonnull final DateRange date) {
        return EventBuilder.builderWithRandomId(date).toBirth(id);
    }

}
