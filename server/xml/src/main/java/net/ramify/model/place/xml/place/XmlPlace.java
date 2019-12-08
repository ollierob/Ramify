package net.ramify.model.place.xml.place;

import com.google.common.collect.Sets;
import net.ramify.model.ParserContext;
import net.ramify.model.date.DateRange;
import net.ramify.model.date.parse.DateParser;
import net.ramify.model.date.xml.XmlBeforeYear;
import net.ramify.model.date.xml.XmlBetweenYears;
import net.ramify.model.date.xml.XmlDateRange;
import net.ramify.model.date.xml.XmlInYear;
import net.ramify.model.place.DefaultPlaceHistory;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroup;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.history.PlaceHistory;
import net.ramify.model.place.provider.PlaceProvider;
import net.ramify.model.place.region.iso.CountryIso;
import net.ramify.model.place.xml.PlaceParserContext;
import net.ramify.model.place.xml.place.settlement.XmlSettlement;
import net.ramify.model.place.xml.place.uk.XmlUkPlace;
import net.ramify.model.place.xml.place.usa.XmlUsaPlace;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.Collection;
import java.util.Set;
import java.util.function.Consumer;

import static net.ramify.utils.StringUtils.isEmpty;

@XmlSeeAlso({XmlSettlement.class, XmlUkPlace.class, XmlUsaPlace.class})
public abstract class XmlPlace {

    public static final String NAMESPACE = "http://ramify.net/places";

    @XmlAttribute(name = "id", required = true)
    private String id;

    @XmlAttribute(name = "name")
    private String name;

    @XmlElements({
            @XmlElement(name = "openedInYear", type = XmlInYear.class, namespace = XmlDateRange.NAMESPACE),
    })
    private XmlDateRange opened;

    @XmlElements({
            @XmlElement(name = "closedInYear", type = XmlInYear.class, namespace = XmlDateRange.NAMESPACE),
            @XmlElement(name = "closedBetweenYears", type = XmlBetweenYears.class, namespace = XmlDateRange.NAMESPACE),
            @XmlElement(name = "closedBeforeYear", type = XmlBeforeYear.class, namespace = XmlDateRange.NAMESPACE)
    })
    private XmlDateRange closed;

    @XmlAttribute(name = "major")
    private Boolean major;

    protected PlaceId placeId(final PlaceParserContext context) {
        return this.placeId(context.countryIso());
    }

    @Nonnull
    public PlaceId placeId(final CountryIso iso) {
        return this.placeId(id, iso);
    }

    protected abstract PlaceId placeId(String id, CountryIso iso);

    protected String id() {
        return id;
    }

    public String name() {
        return name;
    }

    public Set<Place> places(final PlaceParserContext context) {
        final var places = Sets.<Place>newHashSet();
        final var id = this.placeId(context.countryIso());
        final var parent = this.parent(id, context.places());
        this.addPlaces(parent, places::add, context);
        return places;
    }

    private Place parent(final PlaceId id, final PlaceProvider placeProvider) {
        return placeProvider.maybeGet(id).map(Place::parent).orElse(null);
    }

    void addPlaces(final Place parent, final Consumer<Place> addPlace, final PlaceParserContext context) {
        try {
            final var self = this.place(parent, context);
            addPlace.accept(self);
            final var children = this.children();
            if (children != null) children.forEach(child -> child.addPlaces(self, addPlace, context));
        } catch (final Place.InvalidPlaceTypeException | RuntimeException rex) {
            throw new RuntimeException("Error reading " + this, rex);
        }
    }

    private Place place(final Place parent, final PlaceParserContext context) throws Place.InvalidPlaceTypeException {
        final var id = this.placeId(context.countryIso());
        return isEmpty(name)
                ? context.places().require(id)
                : this.place(parent, context.group(id).map(PlaceGroup::id).orElse(null), this.history(context), context);
    }

    @Nonnull
    protected abstract Place place(Place parent, PlaceGroupId groupId, PlaceHistory history, PlaceParserContext context) throws Place.InvalidPlaceTypeException;

    @CheckForNull
    protected abstract Collection<? extends XmlPlace> children();

    protected DateRange opened(final DateParser dates) {
        return opened == null ? null : opened.resolve(dates);
    }

    protected DateRange closed(final DateParser dates) {
        return closed == null ? null : closed.resolve(dates);
    }

    protected PlaceHistory history(final ParserContext context) {
        return opened == null && closed == null
                ? null
                : new DefaultPlaceHistory(this.opened(context.dateParser()), this.closed(context.dateParser()));
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "[" + id + "]";
    }

}
