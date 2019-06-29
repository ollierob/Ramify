package net.ramify.model.place.xml.place;

import com.google.common.collect.Sets;
import net.ramify.model.place.PlaceId;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;
import java.util.Set;

@XmlType(namespace = XmlPlace.NAMESPACE, name = "places")
@XmlRootElement(name = "places")
public class XmlPlaces {

    @XmlElementRef
    private List<XmlPlace> places;

    @Nonnull
    public Set<PlaceId> placeIds() {
        final var ids = Sets.<PlaceId>newHashSet();
        places.forEach(place -> ids.addAll(place.placeIds()));
        return ids;
    }

}
