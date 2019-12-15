package net.ramify.model.event.collection;

import javax.annotation.Nonnull;

public interface HasPersonEvents extends HasEvents {

    @Nonnull
    PersonEventSet events();

}
