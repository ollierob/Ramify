package net.ramify.model.place.region;

import net.ramify.model.place.PlaceId;

import javax.annotation.Nonnull;

public class Hundred extends AbstractRegion {

    private final County parent;

    public Hundred(final PlaceId id, final String name, final County parent) {
        super(id, name);
        this.parent = parent;
    }

    @Nonnull
    @Override
    public County parent() {
        return parent;
    }

}
