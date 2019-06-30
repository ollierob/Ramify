package net.ramify.model.place.xml.place;

import com.google.common.collect.Sets;
import net.ramify.model.place.HasPlaceId;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.provider.PlaceProvider;
import net.ramify.utils.objects.Functions;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.Collection;
import java.util.Set;
import java.util.function.Consumer;

@XmlSeeAlso({XmlCountry.class, XmlState.class, XmlCountryCounty.class, XmlParish.class})
public abstract class XmlPlace implements HasPlaceId {

    public static final String NAMESPACE = "http://ramify.net/places";

    @XmlAttribute(name = "id", required = true)
    private String id;

    @XmlAttribute(name = "name")
    private String name;

    abstract PlaceId placeId(String id);

    @Nonnull
    @Override
    public PlaceId placeId() {
        return this.placeId(id);
    }

    public String name() {
        return name;
    }

    public Set<Place> places(final PlaceProvider placeProvider) {
        final var places = Sets.<Place>newHashSet();
        final var id = this.placeId();
        final var parent = this.parent(id, placeProvider);
        this.addPlaces(placeProvider, parent, places::add);
        return places;
    }

    private Place parent(final PlaceId id, final PlaceProvider placeProvider) {
        return Functions.ifNonNull(placeProvider.get(id), Place::parent);
    }

    private void addPlaces(final PlaceProvider placeProvider, final Place parent, final Consumer<Place> addPlace) {
        try {
            final var self = this.place(placeProvider, parent);
            addPlace.accept(self);
            this.children().forEach(child -> child.addPlaces(placeProvider, self, addPlace));
        } catch (final Place.InvalidPlaceTypeException | RuntimeException rex) {
            throw new RuntimeException("Error reading " + this, rex);
        }
    }

    private Place place(final PlaceProvider placeProvider, final Place parent) throws Place.InvalidPlaceTypeException {
        return name == null ? placeProvider.require(this.placeId()) : this.place(parent);
    }

    @Nonnull
    abstract Place place(Place parent) throws Place.InvalidPlaceTypeException;

    abstract Collection<XmlPlace> children();

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "[" + id + "]";
    }
}
