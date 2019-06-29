package net.ramify.model.place.xml;

import net.ramify.model.place.PlaceId;
import net.ramify.model.place.church.ChurchInfo;
import net.ramify.model.place.church.ChurchInfoProvider;

import javax.annotation.CheckForNull;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Map;

@XmlTransient
class XmlChurchInfoProvider implements ChurchInfoProvider {

    private final Map<PlaceId, ChurchInfo> churches;

    XmlChurchInfoProvider(final Map<PlaceId, ChurchInfo> churches) {
        this.churches = churches;
    }

    @CheckForNull
    @Override
    public ChurchInfo get(final PlaceId id) {
        return churches.get(id);
    }

}
