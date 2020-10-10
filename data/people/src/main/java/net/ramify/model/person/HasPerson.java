package net.ramify.model.person;

import net.ramify.model.event.collection.HasPersonEvents;
import net.ramify.model.event.collection.PersonEventSet;

import javax.annotation.Nonnull;

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
    default PersonEventSet events() {
        return this.person().events();
    }

}
