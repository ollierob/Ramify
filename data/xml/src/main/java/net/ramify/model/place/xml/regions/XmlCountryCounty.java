package net.ramify.model.place.xml.regions;

import net.ramify.model.place.PlaceId;
import net.ramify.model.place.id.Spid;
import net.ramify.model.place.region.CountryCounty;

import javax.annotation.Nonnull;

public class XmlCountryCounty extends XmlRegion {

    @Nonnull
    @Override
    public PlaceId placeId() {
        return new Spid(CountryCounty.class, id);
    }

}
