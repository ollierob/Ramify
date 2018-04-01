package net.ramify.model.person.event;

import net.ramify.model.event.Events;

import javax.annotation.Nonnull;
import java.util.Optional;

public interface PersonEvents extends Events {

    @Nonnull
    default Optional<Birth> birth() {
        return this.earliest(Birth.class);
    }

    @Nonnull
    default Optional<Death> death() {
        return this.latest(Death.class);
    }

}
