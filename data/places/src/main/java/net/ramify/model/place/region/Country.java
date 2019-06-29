package net.ramify.model.place.region;

import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.utils.objects.Functions;

import javax.annotation.CheckForNull;

public class Country extends AbstractRegion {

    private final Country parent;

    public Country(final PlaceId id, final String name) {
        this(id, name, null);
    }

    public Country(final PlaceId id, final String name, final Place parent) {
        this(id, name, Functions.ifNonNull(parent, p -> p.requireAs(Country.class)));
    }

    public Country(final PlaceId id, final String name, final Country parent) {
        super(id, name);
        this.parent = parent;
    }

    @Override
    @CheckForNull
    public Country parent() {
        return parent;
    }

}
