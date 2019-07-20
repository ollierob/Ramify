package net.ramify.model.person;

import net.ramify.model.event.Event;
import net.ramify.model.event.collection.HasPersonEvents;

import javax.annotation.Nonnull;
import java.util.Set;

public interface HasPerson extends HasPersonId, HasPersonEvents {

    @Nonnull
    Person person();

    @Nonnull
    @Override
    default PersonId personId() {
        return this.person().personId();
    }

    @Nonnull
    @Override
    default Set<? extends Event> events() {
        return this.person().events();
    }

}
