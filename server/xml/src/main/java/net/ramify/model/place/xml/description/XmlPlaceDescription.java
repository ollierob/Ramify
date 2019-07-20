package net.ramify.model.place.xml.description;

import com.google.common.collect.Sets;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceDescription;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.provider.PlaceProvider;
import net.ramify.model.place.xml.place.XmlPlace;
import net.ramify.model.util.Link;
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

    @XmlElement(name = "laterBecame", namespace = XmlPlace.NAMESPACE)
    private List<XmlPlaceId> laterBecame;

    @XmlElement(name = "alsoSee", namespace = XmlPlace.NAMESPACE)
    private List<XmlPlaceId> alsoSee;

    @XmlElementRef
    private List<XmlLink> links;

    @XmlElement(name = "description", required = true, namespace = XmlPlace.NAMESPACE)
    private String description;

    @Nonnull
    PlaceId placeId() {
        return new PlaceId(id);
    }

    @Nonnull
    PlaceDescription description(final PlaceProvider places) {
        return new ResolvedPlaceDescription(
                this.placeId(),
                description.trim(),
                this.alsoSee(places),
                this.laterBecame(places),
                this.links());
    }

    @Nonnull
    private Set<Place> alsoSee(final PlaceProvider places) {
        return this.alsoSee == null
                ? Collections.emptySet()
                : SetUtils.transformIgnoreNull(this.alsoSee, id -> places.get(id.placeId()));
    }

    @Nonnull
    private Set<Place> laterBecame(final PlaceProvider places) {
        return this.laterBecame == null
                ? Collections.emptySet()
                : SetUtils.transformIgnoreNull(this.laterBecame, id -> places.get(id.placeId()));
    }

    @Nonnull
    private Set<Link> links() {
        return this.links == null
                ? Collections.emptySet()
                : Sets.newHashSet(this.links);
    }

}
