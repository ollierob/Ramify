package net.ramify.model.place.region;

import net.ramify.model.place.PlaceId;

public class CountryCounty extends AbstractRegion {

    private final Country country;

    public CountryCounty(final PlaceId id, final String name, final Country country) {
        super(id, name);
        this.country = country;
    }

    @Override
    public Country parent() {
        return country;
    }

}
