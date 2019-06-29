package net.ramify.model.place.xml;

import net.ramify.model.place.PlaceId;
import net.ramify.model.place.church.Church;
import net.ramify.model.place.church.ChurchProvider;

import javax.annotation.CheckForNull;
import java.util.Map;

class XmlChurchProvider implements ChurchProvider {

    private final Map<PlaceId, Church> churches;

    XmlChurchProvider(final Map<PlaceId, Church> churches) {
        this.churches = churches;
    }

    @CheckForNull
    @Override
    public Church get(final PlaceId id) {
        return churches.get(id);
    }

}
