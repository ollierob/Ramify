package net.ramify.model.place;

import javax.annotation.Nonnull;
import java.util.Set;

public interface Locations {

    @Nonnull
    Set<? extends Location> locations();

}
