package net.ramify.model.person.age;

import javax.annotation.Nonnull;
import java.util.Optional;

public interface HasAges {

    @Nonnull
    Optional<Age> givenAge();

    @Nonnull
    Optional<Age> computedAge();

}
