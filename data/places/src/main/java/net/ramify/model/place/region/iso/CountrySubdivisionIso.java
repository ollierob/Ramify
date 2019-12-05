package net.ramify.model.place.region.iso;

public class CountrySubdivisionIso extends CountryIso {

    public static CountrySubdivisionIso valueOf(final String iso) {
        final var country = CountryIso.valueOf(iso);
        return country instanceof CountrySubdivisionIso ? (CountrySubdivisionIso) country : null;
    }

    private final String country;

    CountrySubdivisionIso(final String country, final String subdivision) {
        super(country + '-' + subdivision);
        this.country = country;
    }

    @Override
    public CountryIso withoutSubdivision() {
        return CountryIso.valueOf(country);
    }

}
