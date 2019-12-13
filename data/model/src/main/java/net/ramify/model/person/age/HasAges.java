package net.ramify.model.person.age;

import javax.annotation.Nonnull;
import java.util.Optional;

public interface HasAges {

    @Nonnull
    default Optional<Age> givenAge() {
        return Optional.empty();
    }

    @Nonnull
    default Optional<Age> computedAge() {
        return Optional.empty();
    }

}
