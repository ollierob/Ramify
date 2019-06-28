package net.ramify.model.place.xml.church;

import net.ramify.model.date.DateRange;
import net.ramify.model.date.xml.XmlDateRange;
import net.ramify.model.place.HasPlaceId;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.church.Church;
import net.ramify.model.place.id.Spid;
import net.ramify.model.place.provider.PlaceProvider;
import net.ramify.model.place.type.Building;
import net.ramify.utils.objects.Functions;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
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
        return Spid.church(placeId);
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

    private static class ResolvedChurch implements Church {

        private final XmlChurch xml;
        private final Building building;

        ResolvedChurch(final XmlChurch xml, final Building building) {
            this.xml = xml;
            this.building = building;
        }

        @Nonnull
        @Override
        public Building place() {
            return building;
        }

        @CheckForNull
        @Override
        public String denomination() {
            return xml.denomination();
        }

        @Nonnull
        @Override
        public DateRange founded() {
            return xml.founded();
        }

        @CheckForNull
        @Override
        public DateRange closed() {
            return xml.closed();
        }

    }

}
