package net.ramify.server.resource.places.churches;

import com.google.common.collect.ImmutableTable;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.id.Spid;
import net.ramify.model.place.proto.PlaceProto;
import net.ramify.model.place.region.Country;

import javax.annotation.CheckForNull;

class PlaceTypeDescriptions {

    private static final PlaceId ENGLAND = new Spid(Country.class, "england");

    private static final ImmutableTable<PlaceProto.PlaceType, PlaceId, String> DESCRIPTIONS;

    static {
        final var descriptions = ImmutableTable.<PlaceProto.PlaceType, PlaceId, String>builder();
        descriptions.put(PlaceProto.PlaceType.TOWNSHIP, ENGLAND, "An English township was a subdivision of a parish.");
        DESCRIPTIONS = descriptions.build();
    }

    @CheckForNull
    static String describe(final PlaceProto.PlaceType type, final Country country) {
        return DESCRIPTIONS.get(type, country.placeId());
    }

}
