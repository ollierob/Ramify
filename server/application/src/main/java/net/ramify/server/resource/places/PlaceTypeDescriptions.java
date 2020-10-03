package net.ramify.server.resource.places;

import com.google.common.collect.ImmutableTable;
import net.ramify.model.place.iso.CountryIso;
import net.ramify.model.place.iso.CountrySubdivisionIso;
import net.ramify.model.place.proto.PlaceProto;
import net.ramify.model.place.region.Chapelry;
import net.ramify.model.place.region.Township;

import javax.annotation.CheckForNull;

class PlaceTypeDescriptions {

    private static final ImmutableTable<PlaceProto.PlaceType, CountryIso, String> DESCRIPTIONS;

    static {
        final var descriptions = ImmutableTable.<PlaceProto.PlaceType, CountryIso, String>builder();
        descriptions.put(Chapelry.PLACE_TYPE, CountrySubdivisionIso.GB_ENG, "A _chapelry_ was a subdivision of a parish, consisting of one or more townships, and centered around a *chapel of ease* (church).");
        descriptions.put(Township.PLACE_TYPE, CountrySubdivisionIso.GB_ENG, "An English _township_ was a rural subdivision of a parish or a chapelry. It was frequently (but not necessarily) centered around a town or village.");
        DESCRIPTIONS = descriptions.build();
    }

    @CheckForNull
    static String describe(final PlaceProto.PlaceType type, final CountryIso country) {
        return DESCRIPTIONS.get(type, country);
    }

}
