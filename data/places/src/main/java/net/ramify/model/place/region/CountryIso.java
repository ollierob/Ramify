package net.ramify.model.place.region;

import net.ramify.model.util.Iso;

import javax.annotation.Nonnull;

public class CountryIso extends Iso {

    public static final CountryIso GB = new CountryIso("GB");
    public static final CountryIso US = new CountryIso("US");

    CountryIso(@Nonnull final String value) {
        super(value);
    }

}
