package net.ramify.model.place.xml.church;

import net.ramify.model.place.PlaceId;
import net.ramify.model.place.church.Church;
import net.ramify.model.place.church.ChurchProvider;

import javax.annotation.CheckForNull;

public class XmlChurchProvider implements ChurchProvider {

    @CheckForNull
    @Override
    public Church get(final PlaceId id) {
        throw new UnsupportedOperationException(); //TODO
    }

}
