package net.ramify.model.place.xml.place.building;

import net.ramify.model.date.DateRange;
import net.ramify.model.date.parse.DateParser;
import net.ramify.model.date.xml.XmlBeforeYear;
import net.ramify.model.date.xml.XmlBetweenYears;
import net.ramify.model.date.xml.XmlDateRange;
import net.ramify.model.date.xml.XmlInYear;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.building.DefaultBuildingHistory;
import net.ramify.model.place.history.BuildingHistory;
import net.ramify.model.place.history.PlaceHistory;
import net.ramify.model.place.iso.CountryIso;
import net.ramify.model.place.xml.PlaceParserContext;
import net.ramify.model.place.xml.place.XmlPlace;

import javax.annotation.CheckForNull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Collection;
import java.util.Collections;

@XmlTransient
@XmlSeeAlso({XmlChurch.class, XmlFarmstead.class, XmlGraveyard.class, XmlInn.class, XmlMill.class, XmlMonastery.class, XmlSchool.class, XmlStreet.class, XmlWorkhouse.class})
public abstract class XmlBuilding<P extends Place> extends XmlPlace {

    @XmlAttribute(name = "countryIso")
    private String countryIso;

    @XmlElements({
            @XmlElement(name = "builtInYear", type = XmlInYear.class, namespace = XmlDateRange.NAMESPACE),
            @XmlElement(name = "builtBetwweenYears", type = XmlBetweenYears.class, namespace = XmlDateRange.NAMESPACE),
            @XmlElement(name = "builtBeforeYear", type = XmlBeforeYear.class, namespace = XmlDateRange.NAMESPACE)
    })
    private XmlDateRange built;

    @XmlAttribute(name = "demolished", required = false)
    private Boolean demolished;

    private final Class<P> type;

    XmlBuilding(final Class<P> type) {
        this.type = type;
    }

    protected PlaceId placeId(final String id, final CountryIso countryIso) {
        return new PlaceId(countryIso, type, id);
    }

    @Override
    protected P toPlace(
            final Place parent,
            final PlaceGroupId groupId,
            final PlaceHistory history,
            final PlaceParserContext context)
            throws Place.InvalidPlaceTypeException {
        return this.toPlace(parent, groupId, history instanceof BuildingHistory ? (BuildingHistory) history : this.history(context), context);
    }

    protected abstract P toPlace(
            Place parent,
            PlaceGroupId groupId,
            BuildingHistory history,
            PlaceParserContext context)
            throws Place.InvalidPlaceTypeException;

    @Override
    protected Collection<? extends XmlPlace> children() {
        return Collections.emptySet();
    }

    @CheckForNull
    protected DateRange built(final DateParser dates) {
        return built == null ? null : built.resolve(dates);
    }

    protected boolean isDemolished() {
        return demolished == Boolean.TRUE;
    }

    @Override
    protected BuildingHistory history(final PlaceParserContext context) {
        return new DefaultBuildingHistory(this.built(context.dateParser()), this.closed(context.dateParser()), this.isDemolished());
    }

}
