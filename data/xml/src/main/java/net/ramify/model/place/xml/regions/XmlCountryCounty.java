package net.ramify.model.place.xml.regions;

import net.ramify.model.place.PlaceId;
import net.ramify.model.place.id.Spid;
import net.ramify.model.place.region.CountryCounty;

public class XmlCountryCounty extends XmlRegion {

    @Override
    PlaceId placeId(final String id) {
        return new Spid(CountryCounty.class, id);
    }

}
