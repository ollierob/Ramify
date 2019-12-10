package net.ramify.model.person;

import net.ramify.model.person.collection.HasPersonIds;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.Set;

public interface HasPersonId extends HasPersonIds {

    @Nonnull
    PersonId personId();

    @Nonnull
    @Deprecated
    @Override
    default Set<PersonId> personIds() {
        return Collections.singleton(this.personId());
    }

}