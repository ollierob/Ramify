package net.ramify.model.place;

import com.google.common.collect.ImmutableSet;
import net.ramify.model.place.iso.CountryIso;
import net.ramify.model.place.iso.CountrySubdivisionIso;
import net.ramify.model.place.region.Chapelry;
import net.ramify.model.place.region.Hundred;
import net.ramify.model.place.region.Parish;
import net.ramify.model.place.region.Township;
import net.ramify.model.place.region.district.RuralDistrict;
import net.ramify.model.place.region.district.UrbanDistrict;
import net.ramify.model.place.region.manor.Graveship;
import net.ramify.model.place.region.manor.Manor;

import javax.annotation.Nonnull;

public class DefunctPlaces {

    private static final ImmutableSet<Class<? extends Place>> DEFUNCT_ENGLAND_TYPES = ImmutableSet.of(
            Parish.class, Chapelry.class, Township.class,
            RuralDistrict.class, UrbanDistrict.class,
            Hundred.class,
            Manor.class, Graveship.class);

    public static boolean isDefunct(@Nonnull final Place place, final CountryIso iso) {
        if (iso == null) return false;
        if (CountrySubdivisionIso.GB_ENG.equals(iso)) return isDefunctInEngland(place);
        return false;
    }

    private static boolean isDefunctInEngland(final Place place) {
        return place.isAny(DEFUNCT_ENGLAND_TYPES);
    }

}
