package net.ramify.model.place.iso;

import static net.ramify.utils.StringUtils.isBlank;

public class CountrySubdivisionIso extends CountryIso {

    public static final CountrySubdivisionIso GB_ENG = new CountrySubdivisionIso("GB-ENG");
    public static final CountrySubdivisionIso GB_SCT = new CountrySubdivisionIso("GB-SCT");

    public static CountrySubdivisionIso valueOf(String iso) {
        if (isBlank(iso) || iso.indexOf('-') < 0) return null;
        iso = iso.trim().toUpperCase();
        switch (iso) {
            case "GB-ENG":
                return GB_ENG;
            case "GB-SCT":
                return GB_SCT;
            default:
                return new CountrySubdivisionIso(iso);
        }
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
