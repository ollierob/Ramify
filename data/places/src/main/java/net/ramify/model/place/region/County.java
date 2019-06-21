package net.ramify.model.place.region;

import net.ramify.model.place.PlaceId;
import net.ramify.model.place.type.Country;
import net.ramify.model.place.type.Region;

import javax.annotation.Nonnull;

public class County extends AbstractRegion {

    private final Country country;
    private final Region parent;

    public County(final PlaceId id, final String name, final Country country) {
        super(id, name);
        this.country = country;
        this.parent = this;
    }

    public County(final PlaceId id, final String name, final Region parent) {
        super(id, name);
        this.country = parent.country();
        this.parent = parent;
    }

    @Override
    public Country country() {
        return country;
    }

    @Nonnull
    @Override
    public Region parent() {
        return parent;
    }

}
