package net.ramify.model.place.iso;

public class CountrySubdivisionIso extends CountryIso {

    public static final CountrySubdivisionIso GB_ENG = new CountrySubdivisionIso("GB-ENG");

    public static CountrySubdivisionIso valueOf(final String iso) {
        final var country = CountryIso.valueOf(iso);
        return country instanceof CountrySubdivisionIso ? (CountrySubdivisionIso) country : null;
    }

    private final String country;

    CountrySubdivisionIso(final String iso) {
        super(iso);
        this.country = iso.substring(0, iso.indexOf('-'));
    }

    CountrySubdivisionIso(final String country, final String subdivision) {
        super(country + '-' + subdivision);
        this.country = country;
    }

    @Override
    public CountryIso withoutSubdivision() {
        return CountryIso.valueOf(country);
    }

    @Override
    public boolean contains(final CountryIso that) {
        return this.equals(that);
    }

}
