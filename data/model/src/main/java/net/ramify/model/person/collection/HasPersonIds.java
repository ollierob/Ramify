package net.ramify.model.person.collection;

import net.ramify.model.person.PersonId;

import javax.annotation.Nonnull;
import java.util.Set;

public interface HasPersonIds {

    @Nonnull
    Set<PersonId> personIds();

}
