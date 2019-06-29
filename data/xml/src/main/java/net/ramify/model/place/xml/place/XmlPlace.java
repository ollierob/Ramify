package net.ramify.model.place.xml.place;

import com.google.common.collect.Maps;
import net.ramify.model.place.HasPlaceId;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.provider.PlaceProvider;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlAttribute;
import java.util.Collection;
import java.util.Map;
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

    public Map<PlaceId, Place> places(final PlaceProvider placeProvider) {
        final var places = Maps.<PlaceId, Place>newHashMap();
        final var id = this.placeId();
        final var parent = placeProvider.get(id);
        this.addPlaces(placeProvider, parent, place -> places.put(place.placeId(), place));
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
