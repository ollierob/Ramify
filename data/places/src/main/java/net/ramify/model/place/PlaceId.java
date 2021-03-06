package net.ramify.model.place;

import com.google.common.base.Preconditions;
import net.ramify.model.Id;
import net.ramify.model.place.iso.CountryIso;

import javax.annotation.Nonnull;

public class PlaceId extends Id implements HasPlaceId, PlaceOrGroupId {

    public static final PlaceId ENGLAND = new PlaceId("GB:england:country");

    private CountryIso iso;
    private String name;
    private PlaceGroupId placeGroupId;

    public PlaceId(final String value) {
        super(value);
        Preconditions.checkArgument(value.indexOf(':') > 0, "Invalid name: %s", value);
    }

    public PlaceId(final CountryIso iso, final Class<? extends Place> type, final String name) {
        super(iso + ":" + name + ":" + type.getSimpleName().toLowerCase());
        this.iso = iso;
    }

    @Override
    public PlaceId placeId() {
        return this;
    }

    public CountryIso countryIso() {
        return iso == null ? iso = parseIso(this.value()) : iso;
    }

    private static CountryIso parseIso(final String id) {
        return CountryIso.valueOf(id.substring(0, id.indexOf(':')));
    }

    public String name() {
        return name == null ? name = parseName(this.value()) : name;
    }

    private static String parseName(final String id) {
        final int a = id.indexOf(':') + 1, b = id.indexOf(':', a);
        return id.substring(a, b);
    }

    @Override
    public @Nonnull PlaceGroupId placeGroupId() {
        return placeGroupId == null
                ? placeGroupId = new PlaceGroupId(this.countryIso(), this.name())
                : placeGroupId;
    }

}
