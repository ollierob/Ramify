package net.ramify.model.place.xml.place;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import net.ramify.model.date.DateRange;
import net.ramify.model.date.parse.DateParser;
import net.ramify.model.date.xml.XmlBeforeYear;
import net.ramify.model.date.xml.XmlBetweenYears;
import net.ramify.model.date.xml.XmlDateRange;
import net.ramify.model.date.xml.XmlInYear;
import net.ramify.model.place.DefaultPlaceHistory;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.history.PlaceHistory;
import net.ramify.model.place.iso.CountryIso;
import net.ramify.model.place.provider.PlaceProvider;
import net.ramify.model.place.xml.PlaceParserContext;
import net.ramify.model.place.xml.place.france.XmlFrance;
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

import static net.ramify.utils.StringUtils.isBlank;

@XmlSeeAlso({XmlSettlement.class, XmlUkPlace.class, XmlUsaPlace.class, XmlFrance.class})
public abstract class XmlPlace {

    public static final String NAMESPACE = "http://ramify.net/places";

    @XmlAttribute(name = "id", required = true)
    private String id;

    @XmlAttribute(name = "groupId")
    private String groupId;

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

    @XmlElement(name = "physicalParent", namespace = NAMESPACE)
    private XmlPlaces within;

    @XmlAttribute(name = "major")
    private Boolean major;

    @Nonnull
    public PlaceId placeId(final PlaceParserContext context) {
        return this.placeId(context.countryIso(false));
    }

    @Nonnull
    protected PlaceId placeId(final CountryIso iso) {
        return this.placeId(id, iso);
    }

    protected abstract PlaceId placeId(String id, CountryIso iso);

    @CheckForNull
    protected PlaceGroupId placeGroupId(final PlaceId placeId) {
        return isBlank(groupId) ? placeId.placeGroupId() : new PlaceGroupId(groupId);
    }

    public String name() {
        return name;
    }

    public Set<Place> places(final PlaceParserContext context) {
        final var places = Sets.<Place>newHashSet();
        final var id = this.placeId(context.countryIso(false));
        final var parent = this.parent(id, context.places());
        this.addPlaces(parent, places::add, context);
        return places;
    }

    private Place parent(final PlaceId id, final PlaceProvider placeProvider) {
        return placeProvider.maybeGet(id).map(Place::parent).orElse(null);
    }

    void addPlaces(final Place parent, final Consumer<Place> addPlace, final PlaceParserContext context) {
        try {
            final var self = this.toPlace(parent, context);
            addPlace.accept(self);
            final var children = this.children();
            if (children != null) children.forEach(child -> child.addPlaces(self, addPlace, context));
        } catch (final Place.InvalidPlaceTypeException | RuntimeException rex) {
            throw new RuntimeException("Error reading " + this, rex);
        }
    }

    private Place toPlace(final Place parent, final PlaceParserContext context) throws Place.InvalidPlaceTypeException {
        final var placeId = this.placeId(context.countryIso(false));
        return isBlank(name)
                ? context.places().require(placeId)
                : this.toPlace(parent, this.placeGroupId(placeId), this.history(context), context);
    }

    @Nonnull
    protected abstract Place toPlace(
            Place parent,
            PlaceGroupId groupId,
            PlaceHistory history,
            PlaceParserContext context) throws Place.InvalidPlaceTypeException;

    @CheckForNull
    protected abstract Collection<? extends XmlPlace> children();

    protected DateRange opened(final DateParser dates) {
        return opened == null ? null : opened.resolve(dates);
    }

    protected DateRange closed(final DateParser dates) {
        return closed == null ? null : closed.resolve(dates);
    }

    protected PlaceHistory history(final PlaceParserContext context) {
        return opened == null && closed == null
                ? null
                : new DefaultPlaceHistory(this.opened(context.dateParser()), this.closed(context.dateParser()));
    }

    @Nonnull
    public Multimap<PlaceId, PlaceId> physicalParents(final PlaceParserContext context) {

        final var children = this.children();
        if (children == null && within == null) return ImmutableMultimap.of();

        final var map = HashMultimap.<PlaceId, PlaceId>create();
        children.forEach(child -> map.putAll(child.physicalParents(context)));
        if (within != null) within.places().forEach(parent -> map.put(parent.placeId(context), this.placeId(context)));
        return map;

    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "[" + id + "]";
    }

}
