package net.ramify.model.person;

import javax.annotation.Nonnull;
import java.util.Set;

public interface HasPeople {

    @Nonnull
    Set<Person> people();

}
