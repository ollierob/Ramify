package net.ramify.model.place.xml.places;

import com.google.common.collect.Sets;
import net.ramify.model.place.HasPlaceId;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlAttribute;
import java.util.Set;

public abstract class XmlPlace implements HasPlaceId {

    @XmlAttribute(name = "id", required = true)
    private String id;

    XmlPlace() {
    }

    abstract PlaceId placeId(String id);

    @Nonnull
    @Override
    public PlaceId placeId() {
        return this.placeId(id);
    }

    abstract Place place(PlaceId id, Place parent);

    @Nonnull
    public Set<PlaceId> placeIds() {
        final var places = Sets.<PlaceId>newHashSet();
        places.add(this.placeId());
        this.addPlaces(places);
        return places;
    }

    abstract void addPlaces(Set<PlaceId> places);

}
