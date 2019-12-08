package net.ramify.server.resource.places;

import com.google.common.collect.ImmutableTable;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.id.Spid;
import net.ramify.model.place.proto.PlaceProto;
import net.ramify.model.place.region.Chapelry;
import net.ramify.model.place.region.Country;
import net.ramify.model.place.region.Township;
import net.ramify.model.place.region.iso.CountryIso;

import javax.annotation.CheckForNull;

class PlaceTypeDescriptions {

    private static final PlaceId ENGLAND = new Spid(CountryIso.GB, Country.class, "england");

    private static final ImmutableTable<PlaceProto.PlaceType, PlaceId, String> DESCRIPTIONS;

    static {
        final var descriptions = ImmutableTable.<PlaceProto.PlaceType, PlaceId, String>builder();
        descriptions.put(Chapelry.PLACE_TYPE, ENGLAND, "A _chapelry_ was a subdivision of a parish, consisting of one or more townships, and centered around a *chapel of ease* (church).");
        descriptions.put(Township.PLACE_TYPE, ENGLAND, "An English _township_ was a rural subdivision of a parish or a chapelry. It was frequently (but not necessarily) centered around a town or village.");
        DESCRIPTIONS = descriptions.build();
    }

    @CheckForNull
    static String describe(final PlaceProto.PlaceType type, final Country country) {
        return DESCRIPTIONS.get(type, country.placeId());
    }

}
