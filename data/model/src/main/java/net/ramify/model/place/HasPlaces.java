package net.ramify.model.place;

import javax.annotation.Nonnull;
import java.util.Set;

public interface HasPlaces {

    @Nonnull
    Set<Place> places();

}
