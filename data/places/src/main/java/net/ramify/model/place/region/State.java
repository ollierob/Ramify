package net.ramify.model.place.region;

import net.ramify.model.place.PlaceId;
import net.ramify.model.place.type.Country;
import net.ramify.model.place.type.Region;

import javax.annotation.Nonnull;

public class State extends AbstractRegion {

    private final Country country;

    public State(final PlaceId id, final String name, final Country country) {
        super(id, name);
        this.country = country;
    }

    @Nonnull
    @Override
    public Region parent() {
        return this;
    }

    @Nonnull
    @Override
    public Country country() {
        return country;
    }

}
