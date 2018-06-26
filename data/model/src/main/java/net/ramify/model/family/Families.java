package net.ramify.model.family;

import javax.annotation.Nonnull;
import java.util.Set;

public interface Families {

    static Families of(final Family family) {
        return () -> Set.of(family);
    }

    @Nonnull
    Set<Family> families();

}
