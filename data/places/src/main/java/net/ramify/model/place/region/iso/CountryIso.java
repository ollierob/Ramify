package net.ramify.model.place.region.iso;

import net.ramify.model.place.Place;
import net.ramify.model.place.region.Country;
import net.ramify.model.util.Iso;

import static net.ramify.utils.StringUtils.isEmpty;

public class CountryIso extends Iso {

    public static final CountryIso GB = new CountryIso("GB");
    public static final CountryIso US = new CountryIso("US");

    public static CountryIso valueOf(final String iso) {
        if (isEmpty(iso)) return null;
        switch (iso.trim().toUpperCase()) {
            case "GB":
                return GB;
            case "US":
                return US;
            default:
                final var dash = iso.indexOf('-');
                return dash <= 0 ? new CountryIso(iso) : new CountrySubdivisionIso(iso.substring(0, dash), iso.substring(dash + 1));
        }
    }

    public static CountryIso readFrom(final Place place) {
        return place == null
                ? null
                : place.find(Country.class).flatMap(Country::iso).map(CountryIso::withoutSubdivision).get();
    }

    public CountryIso(final String country) {
        super(country);
    }

    public CountryIso withoutSubdivision() {
        return this;
    }

}
