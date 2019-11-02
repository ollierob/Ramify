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
import net.ramify.model.place.PlaceHistory;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.provider.PlaceGroupProvider;
import net.ramify.model.place.provider.PlaceProvider;
import net.ramify.model.place.xml.place.settlement.XmlCity;
import net.ramify.model.place.xml.place.settlement.XmlTown;
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

@XmlSeeAlso({XmlCountry.class, XmlState.class, XmlParish.class, XmlHundred.class, XmlTown.class, XmlCity.class})
public abstract class XmlPlace implements HasPlaceId {

    public static final String NAMESPACE = "http://ramify.net/places";

    @XmlAttribute(name = "id", required = true)
    private String id;

    @XmlAttribute(name = "name")
    private String name;

    @XmlElements({
            @XmlElement(name = "closedInYear", type = XmlInYear.class, namespace = XmlDateRange.NAMESPACE),
            @XmlElement(name = "closedBetwweenYears", type = XmlBetweenYears.class, namespace = XmlDateRange.NAMESPACE),
            @XmlElement(name = "closedBeforeYear", type = XmlBeforeYear.class, namespace = XmlDateRange.NAMESPACE)
    })
    private XmlDateRange closed;

    protected abstract PlaceId placeId(String id);

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
        return Functions.ifNonNull(placeProvider.get(id), Place::parent);
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
        final var id = this.placeId();
        return name == null ? placeProvider.require(id) : this.place(parent, Functions.ifNonNull(groupProvider.getGroup(id), PlaceGroup::id), context);
    }

    @Nonnull
    protected abstract Place place(Place parent, PlaceGroupId groupId, ParserContext context) throws Place.InvalidPlaceTypeException;

    @CheckForNull
    protected abstract Collection<? extends XmlPlace> children();

    protected DateRange closed(final DateParser dates) {
        return closed == null ? null : closed.resolve(dates);
    }

    protected PlaceHistory history(final ParserContext context) {
        return closed == null ? null : new DefaultPlaceHistory(null, this.closed(context.dateParser()));
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "[" + id + "]";
    }

}
