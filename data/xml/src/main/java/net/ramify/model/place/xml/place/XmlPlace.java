package net.ramify.model.place.xml.place;

import com.google.common.collect.Sets;
import net.ramify.model.place.HasPlaceId;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.provider.PlaceProvider;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlAttribute;
import java.util.Collection;
import java.util.Set;
import java.util.function.Consumer;

public abstract class XmlPlace implements HasPlaceId {

    public static final String NAMESPACE = "http://ramify.net/places";

    @XmlAttribute(name = "id", required = true)
    private String id;

    @XmlAttribute(name = "name", required = true)
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
        final var parent = placeProvider.get(id);
        this.addPlaces(placeProvider, parent, places::add);
        return places;
    }

    private void addPlaces(final PlaceProvider placeProvider, final Place parent, final Consumer<Place> addPlace) {
        final var self = this.place(parent);
        addPlace.accept(self);
        this.children().forEach(child -> child.addPlaces(placeProvider, self, addPlace));
    }

    abstract Place place(Place parent);

    abstract Collection<XmlPlace> children();

}
