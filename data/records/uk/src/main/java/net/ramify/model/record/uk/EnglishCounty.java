package net.ramify.model.record.uk;

import net.ramify.model.place.County;
import net.ramify.model.place.address.CountyInCountry;
import net.ramify.model.place.address.HasAddress;

import javax.annotation.Nonnull;

public class EnglishCounty extends County implements HasAddress {

    public static final EnglishCounty YORKSHIRE = new EnglishCounty("Yorkshire");

    private CountyInCountry address;

    EnglishCounty(final String name) {
        super(name);
    }

    @Nonnull
    public CountyInCountry address() {
        return address == null
                ? address = new CountyInCountry(this, UkCountry.ENGLAND)
                : address;
    }

}
