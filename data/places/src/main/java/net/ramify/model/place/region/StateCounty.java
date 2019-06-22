package net.ramify.model.place.region;

import net.ramify.model.place.PlaceId;

import javax.annotation.Nonnull;

public class StateCounty extends AbstractRegion {

    private final State state;

    public StateCounty(final PlaceId id, final String name, final State state) {
        super(id, name);
        this.state = state;
    }

    @Nonnull
    @Override
    public State parent() {
        return state;
    }

}
