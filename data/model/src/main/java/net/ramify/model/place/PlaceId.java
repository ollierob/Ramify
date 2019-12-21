package net.ramify.model.place;

import com.google.common.base.Preconditions;
import net.ramify.model.Id;
import net.ramify.model.place.iso.CountryIso;
import net.ramify.model.place.provider.PlaceProvider;

import java.util.Objects;

public class PlaceId extends Id implements HasPlaceId {

    public static final PlaceId ENGLAND = new PlaceId("GB:england:country");

    private CountryIso iso;
    private String name;
    private PlaceGroupId placeGroupId;

    public PlaceId(final String value) {
        super(value);
        Preconditions.checkArgument(value.charAt(2) == ':', "Invalid country ISO: %s", value);
        Preconditions.checkArgument(value.indexOf(':', 3) > 0, "Invalid name: %s", value);
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
        return id.substring(2, id.indexOf(':', 3));
    }

    public boolean isParentOf(final PlaceId that, final PlaceProvider placeProvider) {
        final var thisPlace = placeProvider.get(this);
        if (thisPlace == null) return false;
        final var thatPlace = placeProvider.get(that);
        if (thatPlace == null) return false;
        return thisPlace.isParentOf(thatPlace);
    }

    public PlaceGroupId placeGroupId() {
        return placeGroupId == null ? placeGroupId = new PlaceGroupId(this.countryIso(), this.name()) : placeGroupId;
    }

    @Override
    protected boolean equals(final Id that) {
        return that instanceof PlaceId
                && this.equals((PlaceId) that);
    }

    boolean equals(final PlaceId that) {
        return Objects.equals(this.value(), that.value());
    }

}
