package net.ramify.model.place.xml.regions;

import net.ramify.model.place.PlaceId;
import net.ramify.model.place.id.Spid;
import net.ramify.model.place.region.State;

public class XmlState extends XmlRegion {

    @Override
    PlaceId placeId(final String id) {
        return new Spid(State.class, id);
    }

}
