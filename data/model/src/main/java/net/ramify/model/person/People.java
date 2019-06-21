package net.ramify.model.person;

import javax.annotation.Nonnull;
import java.util.Set;

public interface People {

    @Nonnull
    Set<Person> people();

}
