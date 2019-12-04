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
import net.ramify.model.place.HasPlaceId;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroup;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.history.PlaceHistory;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.provider.PlaceGroupProvider;
import net.ramify.model.place.provider.PlaceProvider;
import net.ramify.model.place.xml.place.settlement.XmlSettlement;
import net.ramify.model.place.xml.place.uk.XmlUkPlace;
import net.ramify.model.place.xml.place.usa.XmlUsaPlace;
import net.ramify.utils.objects.Functions;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.Collection;
import java.util.Set;
import java.util.function.Consumer;

@XmlSeeAlso({XmlSettlement.class, XmlUkPlace.class, XmlUsaPlace.class})
public abstract class XmlPlace implements HasPlaceId {

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

    protected abstract PlaceId placeId(String id);

    protected PlaceId placeId(final String id, final Place parent) {
        return this.placeId(id);
    }

    protected PlaceId placeId(final Place parent) {
        return this.placeId(id, parent);
    }

    @Nonnull
    @Override
    public PlaceId placeId() {
        return this.placeId(id);
    }

    public String name() {
        return name;
    }

    public Set<Place> places(final PlaceProvider placeProvider, final PlaceGroupProvider groupProvider, final ParserContext context) {
        final var places = Sets.<Place>newHashSet();
        final var id = this.placeId();
        final var parent = this.parent(id, placeProvider);
        this.addPlaces(placeProvider, parent, places::add, groupProvider, context);
        return places;
    }

    private Place parent(final PlaceId id, final PlaceProvider placeProvider) {
        return placeProvider.maybeGet(id).map(Place::parent).orElse(null);
    }

    void addPlaces(final PlaceProvider placeProvider, final Place parent, final Consumer<Place> addPlace, final PlaceGroupProvider groupProvider, final ParserContext context) {
        try {
            final var self = this.place(placeProvider, parent, groupProvider, context);
            addPlace.accept(self);
            final var children = this.children();
            if (children != null) children.forEach(child -> child.addPlaces(placeProvider, self, addPlace, groupProvider, context));
        } catch (final Place.InvalidPlaceTypeException | RuntimeException rex) {
            throw new RuntimeException("Error reading " + this, rex);
        }
    }

    private Place place(final PlaceProvider placeProvider, final Place parent, final PlaceGroupProvider groupProvider, final ParserContext context) throws Place.InvalidPlaceTypeException {
        final var id = this.placeId(parent);
        return name == null ? placeProvider.require(id) : this.place(parent, Functions.ifNonNull(groupProvider.getGroup(id), PlaceGroup::id), this.history(context), context);
    }

    @Nonnull
    protected abstract Place place(Place parent, PlaceGroupId groupId, PlaceHistory history, ParserContext context) throws Place.InvalidPlaceTypeException;

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
