package net.ramify.model.place.iso;

import com.google.common.base.Preconditions;
import net.ramify.model.Iso;

import static net.ramify.utils.StringUtils.isBlank;

public class CountryIso extends Iso {

    public static final CountryIso GB = new CountryIso("GB");
    public static final CountryIso US = new CountryIso("US");

    public static CountryIso valueOf(String iso) {
        if (isBlank(iso)) return null;
        iso = iso.trim().toUpperCase();
        switch (iso) {
            case "GB":
                return GB;
            case "US":
                return US;
            default:
                final var dash = iso.indexOf('-');
                return dash < 0
                        ? new CountryIso(iso)
                        : CountrySubdivisionIso.valueOf(iso);
        }
    }

    CountryIso(final String iso) {
        super(iso);
        Preconditions.checkArgument(iso.length() <= 6, "Invalid country ISO: %s", iso);
    }

    public CountryIso withoutSubdivision() {
        return this;
    }

    public boolean contains(final CountryIso that) {
        return this.equals(that.withoutSubdivision());
    }

}
