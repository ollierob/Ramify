package net.ramify.model.place.xml.settlements;

import net.ramify.model.date.DateRange;
import net.ramify.model.date.xml.XmlDateRange;
import net.ramify.model.place.HasPlaceId;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.church.Church;
import net.ramify.model.place.provider.PlaceProvider;
import net.ramify.model.place.type.Building;
import net.ramify.utils.objects.Functions;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

class XmlChurch implements HasPlaceId {

    @XmlAttribute(name = "id", required = true)
    private String placeId;

    @XmlAttribute(name = "parentId", required = true)
    private String parentId;

    @XmlAttribute(name = "denomination")
    private String denomination;

    @XmlElement(name = "founded", required = true)
    private XmlDateRange founded;

    @XmlElement(name = "closed")
    private XmlDateRange closed;

    @Override
    public PlaceId placeId() {
        throw new UnsupportedOperationException(); //TODO
    }

    String denomination() {
        return denomination;
    }

    DateRange founded() {
        return founded.resolve();
    }

    DateRange closed() {
        return Functions.ifNonNull(closed, XmlDateRange::resolve);
    }

    Church resolve(final PlaceProvider<Building> placeProvider) {
        final var place = placeProvider.require(this.placeId());
        return new ResolvedChurch(this, place);
    }

}
