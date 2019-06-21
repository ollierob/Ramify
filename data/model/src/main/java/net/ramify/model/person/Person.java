package net.ramify.model.person;

import net.ramify.model.event.HasEvents;
import net.ramify.model.person.name.Name;

import javax.annotation.Nonnull;

public interface Person {

    @Nonnull
    Name name();

    @Nonnull
    HasEvents events();

}
