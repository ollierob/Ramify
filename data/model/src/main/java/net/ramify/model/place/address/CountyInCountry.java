package net.ramify.model.place.address;

import net.ramify.model.place.Country;
import net.ramify.model.place.County;
import net.ramify.model.place.Place;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.Set;

public class CountyInCountry implements Address {

    private final County county;
    private final Country country;

    public CountyInCountry(final County county, final Country country) {
        this.county = county;
        this.country = country;
    }

    @Nonnull
    public Optional<County> county() {
        return Optional.of(county);
    }

    @Nonnull
    @Override
    public Optional<Country> country() {
        return Optional.of(country);
    }

    @Nonnull
    @Override
    public Set<Place> places() {
        return Set.of(county, country);
    }

}
