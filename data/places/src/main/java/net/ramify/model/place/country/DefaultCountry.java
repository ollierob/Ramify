package net.ramify.model.place.country;

import net.ramify.model.place.AbstractPlace;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.type.Country;

import javax.annotation.Nonnull;

public class DefaultCountry extends AbstractPlace implements Country {

    public DefaultCountry(final PlaceId id, final String name) {
        super(id, name);
    }

    @Override
    public boolean contains(@Nonnull final Place that) {
        return this.equals(that.country());
    }

}
