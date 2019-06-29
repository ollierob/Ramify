package net.ramify.model.place.xml.place;

import com.google.common.collect.Sets;
import net.ramify.model.place.HasPlaceId;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlAttribute;
import java.util.Set;

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

    abstract Place place(PlaceId id, String name, Place parent);

    @Nonnull
    public Set<PlaceId> placeIds() {
        final var places = Sets.<PlaceId>newHashSet();
        places.add(this.placeId());
        this.addPlaces(places);
        return places;
    }

    abstract void addPlaces(Set<PlaceId> places);

}
