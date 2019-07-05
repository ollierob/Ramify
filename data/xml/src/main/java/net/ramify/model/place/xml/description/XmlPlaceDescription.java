package net.ramify.model.place.xml.description;

import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceDescription;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.provider.PlaceProvider;
import net.ramify.model.place.xml.place.XmlPlace;
import net.ramify.utils.collections.SetUtils;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@XmlRootElement(namespace = XmlPlace.NAMESPACE, name = "placeDescription")
class XmlPlaceDescription {

    @XmlAttribute(name = "id", required = true)
    private String id;

    @XmlElementRef
    private List<XmlAlsoSeeId> alsoSee;

    @XmlElement(name = "description", required = true, namespace = XmlPlace.NAMESPACE)
    private String description;

    @Nonnull
    PlaceId placeId() {
        return new PlaceId(id);
    }

    @Nonnull
    PlaceDescription description(final PlaceProvider placeProvider) {
        return new ResolvedPlaceDescription(
                this.placeId(),
                description.trim(),
                this.alsoSee(placeProvider));
    }

    private Set<Place> alsoSee(PlaceProvider placeProvider) {
        return this.alsoSee == null
                ? Collections.emptySet()
                : SetUtils.transformIgnoreNull(this.alsoSee, id -> placeProvider.get(id.placeId()));
    }

}
