package net.ramify.model.place.iso;

import com.google.common.base.Preconditions;

public class CountrySubdivisionIso extends CountryIso {

    public static CountrySubdivisionIso valueOf(final String iso) {
        final var country = CountryIso.valueOf(iso);
        return country instanceof CountrySubdivisionIso ? (CountrySubdivisionIso) country : null;
    }

    private final String country;

    CountrySubdivisionIso(final String country, final String subdivision) {
        super(country + '-' + subdivision);
        Preconditions.checkArgument(country.length() == 2, "Invalid country ISO: %s", country);
        Preconditions.checkArgument(subdivision.length() <= 3, "Invalid country subdivision ISO: %s-%s", country, subdivision);
        this.country = country;
    }

    @Override
    public CountryIso withoutSubdivision() {
        return CountryIso.valueOf(country);
    }

}
