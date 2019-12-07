package net.ramify.model.place.xml.place.uk.district;

import com.google.common.base.MoreObjects;
import net.ramify.model.ParserContext;
import net.ramify.model.date.InYears;
import net.ramify.model.place.DefaultPlaceHistory;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.history.PlaceHistory;
import net.ramify.model.place.region.district.Borough;
import net.ramify.model.place.region.iso.CountrySubdivisionIso;
import net.ramify.model.place.xml.PlaceParserContext;
import net.ramify.model.place.xml.place.XmlArea;
import net.ramify.model.place.xml.place.XmlPlace;
import net.ramify.model.place.xml.place.uk.XmlUkPlace;

import javax.annotation.CheckForNull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.Collections;

@XmlRootElement(namespace = XmlUkPlace.NAMESPACE, name = "londonBorough")
public class XmlLondonBorough extends XmlArea<Borough> {

    static final PlaceHistory DEFAULT_HISTORY = new DefaultPlaceHistory(new InYears(1965), null);

    @XmlAttribute(name = "iso")
    private String iso;

    XmlLondonBorough() {
        super(Borough.class);
    }

    @Override
    protected Borough place(
            final Place parent,
            final PlaceGroupId groupId,
            final PlaceHistory history,
            final PlaceParserContext context) throws Place.InvalidPlaceTypeException {
        return new Borough(this.placeId(context), this.name(), parent, groupId, this.iso(), history);
    }

    @Override
    protected PlaceHistory history(final ParserContext context) {
        return MoreObjects.firstNonNull(super.history(context), DEFAULT_HISTORY);
    }

    CountrySubdivisionIso iso() {
        return CountrySubdivisionIso.valueOf(iso);
    }

    @CheckForNull
    @Override
    protected Collection<? extends XmlPlace> children() {
        return Collections.emptyList(); //TODO
    }

}
