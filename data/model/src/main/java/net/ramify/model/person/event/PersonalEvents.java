package net.ramify.model.person.event;

import net.ramify.model.event.Event;
import net.ramify.model.event.Events;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.Set;

/**
 * Life events for a single person.
 */
public interface PersonalEvents extends Events {

    @Nonnull
    default Optional<Birth> birth() {
        return this.earliest(Birth.class);
    }

    @Nonnull
    default Optional<Death> death() {
        return this.latest(Death.class);
    }

    static PersonalEvents of(final Event event) {
        return new SinglePersonalEvent(event);
    }

    PersonalEvents NONE = Set::of;

}
