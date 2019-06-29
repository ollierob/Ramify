package net.ramify.model.place.xml.place;

import com.google.common.collect.Maps;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.provider.PlaceProvider;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;
import java.util.Map;

@XmlType(namespace = XmlPlace.NAMESPACE, name = "places")
@XmlRootElement(name = "places")
public class XmlPlaces {

    @XmlElementRef
    private List<XmlPlace> places;

    @Nonnull
    public Map<PlaceId, Place> places(final PlaceProvider placeProvider) {
        final var places = Maps.<PlaceId, Place>newHashMap();
        this.places.forEach(place -> places.putAll(place.places(placeProvider)));
        return places;
    }

}
