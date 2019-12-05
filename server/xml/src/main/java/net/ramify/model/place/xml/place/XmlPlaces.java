package net.ramify.model.place.xml.place;

import com.google.common.collect.Sets;
import net.ramify.model.place.Place;
import net.ramify.model.place.xml.PlaceParserContext;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@XmlType(namespace = XmlPlace.NAMESPACE, name = "places")
@XmlRootElement(name = "places")
public class XmlPlaces {

    @XmlElementRef
    private List<XmlPlace> places;

    @Nonnull
    public Set<Place> places(final PlaceParserContext context) {
        if (places == null) return Collections.emptySet();
        final var places = Sets.<Place>newHashSet();
        this.places.forEach(place -> places.addAll(place.places(context)));
        return places;
    }

}
