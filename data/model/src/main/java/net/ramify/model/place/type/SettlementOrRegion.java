package net.ramify.model.place.type;

import net.ramify.model.place.Place;

import javax.annotation.Nonnull;

public interface SettlementOrRegion extends Place, HasCountry {

    @Nonnull
    Region region();

}
