package net.ramify.model.place.xml.place.settlement;

import net.ramify.model.ParserContext;
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
import net.ramify.model.place.id.Spid;
import net.ramify.model.place.type.BuildingHistory;
import net.ramify.model.place.xml.place.XmlPlace;

import javax.annotation.CheckForNull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Collection;
import java.util.Collections;

@XmlTransient
abstract class XmlBuilding<P extends Place> extends XmlPlace {

    @XmlElements({
            @XmlElement(name = "builtInYear", type = XmlInYear.class, namespace = XmlDateRange.NAMESPACE),
            @XmlElement(name = "builtBetwweenYears", type = XmlBetweenYears.class, namespace = XmlDateRange.NAMESPACE),
            @XmlElement(name = "builtBeforeYear", type = XmlBeforeYear.class, namespace = XmlDateRange.NAMESPACE)
    })
    private XmlDateRange built;

    @XmlElements({
            @XmlElement(name = "closedInYear", type = XmlInYear.class, namespace = XmlDateRange.NAMESPACE),
            @XmlElement(name = "closedBetwweenYears", type = XmlBetweenYears.class, namespace = XmlDateRange.NAMESPACE),
            @XmlElement(name = "closedBeforeYear", type = XmlBeforeYear.class, namespace = XmlDateRange.NAMESPACE)
    })
    private XmlDateRange closed;

    @XmlAttribute(name = "demolished", required = false)
    private Boolean demolished;

    private final Class<P> type;

    XmlBuilding(final Class<P> type) {
        this.type = type;
    }

    @Override
    protected PlaceId placeId(final String id) {
        return new Spid(type, id);
    }

    @Override
    protected abstract P place(
            Place parent,
            PlaceGroupId groupId,
            ParserContext context)
            throws Place.InvalidPlaceTypeException;

    @Override
    protected Collection<XmlPlace> children() {
        return Collections.emptySet();
    }

    @CheckForNull
    protected DateRange built(final DateParser dates) {
        return built == null ? null : built.resolve(dates);
    }

    protected DateRange closed(final DateParser dates) {
        return closed == null ? null : closed.resolve(dates);
    }

    protected boolean isDemolished() {
        return demolished == Boolean.TRUE;
    }

    protected BuildingHistory history(final ParserContext context) {
        return new DefaultBuildingHistory(this.built(context.dateParser()), this.closed(context.dateParser()), this.isDemolished());
    }

}
