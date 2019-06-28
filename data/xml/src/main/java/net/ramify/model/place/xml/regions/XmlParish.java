package net.ramify.model.place.xml.regions;

import net.ramify.model.place.PlaceId;
import net.ramify.model.place.id.Spid;
import net.ramify.model.place.region.Parish;

public class XmlParish extends XmlRegion {

    @Override
    PlaceId placeId(final String id) {
        return new Spid(Parish.class, id);
    }

}
