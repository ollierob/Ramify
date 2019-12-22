package net.ramify.model.place;

import com.google.common.base.Preconditions;
import net.ramify.model.Id;
import net.ramify.model.place.iso.CountryIso;

import javax.annotation.Nonnull;
import java.util.Objects;

import static net.ramify.utils.StringUtils.isBlank;

public class PlaceGroupId extends Id {

    private CountryIso iso;
    private String name;

    public PlaceGroupId(@Nonnull final String value) {
        super(value);
        Preconditions.checkArgument(value.charAt(2) == ':', "Invalid country ISO: %s", value);
        Preconditions.checkArgument(value.indexOf(':', 3) < 0, "Invalid name: %s", value);
    }

    public PlaceGroupId(final CountryIso iso, final String name) {
        super(iso + ":" + name);
        this.iso = Objects.requireNonNull(iso);
        this.name = Objects.requireNonNull(name);
    }

    public CountryIso countryIso() {
        return iso;
    }

    public String name() {
        return name == null ? name = parseName(this.value()) : name;
    }

    private static String parseName(final String id) {
        return id.substring(id.indexOf(':'));
    }

    public PlaceId withPlaceType(final String type) {
        Preconditions.checkArgument(!isBlank(type), "Invalid (empty) type");
        return new PlaceId(this.value() + ':' + type);
    }

}
