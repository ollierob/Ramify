package net.ramify.model.place.address;

import javax.annotation.Nonnull;
import java.util.Set;

public interface Addresses {

    @Nonnull
    Set<? extends Address> addresses();

}
