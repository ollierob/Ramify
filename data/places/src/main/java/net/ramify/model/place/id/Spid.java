package net.ramify.model.place.id;

import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.region.Country;

public class Spid extends PlaceId {

    public static final Spid ENGLAND = new Spid(Country.class, "england");

    private final Class<? extends Place> type;

    public Spid(final Class<? extends Place> type, final String value) {
        super(type.getSimpleName().toLowerCase() + ":" + value);
        this.type = type;
    }

}
