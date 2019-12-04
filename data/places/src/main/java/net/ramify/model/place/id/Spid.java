package net.ramify.model.place.id;

import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.region.Country;
import net.ramify.model.place.region.CountryIso;

import javax.annotation.Nonnull;

public class Spid extends PlaceId {

    public static final Spid ENGLAND = new Spid(CountryIso.GB, Country.class, "england");

    private final CountryIso iso;
    private final Class<? extends Place> type;

    public Spid(final CountryIso iso, final Class<? extends Place> type, final String value) {
        super(iso + ":" + type.getSimpleName().toLowerCase() + ':' + value);
        this.iso = iso;
        this.type = type;
    }

    @Nonnull
    public CountryIso countryIso() {
        return iso;
    }

    public Class<? extends Place> type() {
        return type;
    }

}
