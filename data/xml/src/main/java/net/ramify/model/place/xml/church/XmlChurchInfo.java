package net.ramify.model.place.xml.church;

import net.ramify.model.date.DateRange;
import net.ramify.model.date.parse.DateParser;
import net.ramify.model.date.xml.XmlBeforeDate;
import net.ramify.model.date.xml.XmlDateRange;
import net.ramify.model.place.HasPlaceId;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.building.Church;
import net.ramify.model.place.church.ChurchInfo;
import net.ramify.model.place.id.Spid;
import net.ramify.model.place.provider.PlaceProvider;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

class XmlChurchInfo implements HasPlaceId {

    @XmlAttribute(name = "id", required = true)
    private String placeId;

    @XmlAttribute(name = "denomination")
    private String denomination;

    @XmlElements({
            @XmlElement(name = "foundedBefore", type = XmlBeforeDate.class)
    })
    private XmlDateRange founded;

    @Override
    public PlaceId placeId() {
        return new Spid(Church.class, placeId);
    }

    String denomination() {
        return denomination;
    }

    @Nonnull
    DateRange founded(final DateParser dateParser) {
        return founded.resolve(dateParser);
    }

    @CheckForNull
    DateRange closed() {
        return null;
    }

    @Nonnull
    ChurchInfo resolve(final PlaceProvider placeProvider, final DateParser dateParser) {
        try {
            final var place = placeProvider.require(this.placeId(), Church.class);
            return new ResolvedChurchInfo(this, place, dateParser);
        } catch (final Place.InvalidPlaceTypeException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

}
