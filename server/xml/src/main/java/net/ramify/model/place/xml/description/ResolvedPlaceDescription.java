package net.ramify.model.place.xml.description;

import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceDescription;
import net.ramify.model.place.PlaceId;
import net.ramify.model.util.Link;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Set;

@XmlTransient
class ResolvedPlaceDescription implements PlaceDescription {

    private final PlaceId placeId;
    private final String description;
    private final Set<Place> alsoSee;
    private final Set<Link> links;

    ResolvedPlaceDescription(
            final PlaceId placeId,
            final String description,
            final Set<Place> alsoSee,
            final Set<Link> links) {
        this.placeId = placeId;
        this.description = description;
        this.alsoSee = alsoSee;
        this.links = links;
    }

    @Nonnull
    @Override
    public PlaceId placeId() {
        return placeId;
    }

    @Nonnull
    @Override
    public String description() {
        return description;
    }

    @Nonnull
    @Override
    public Set<Place> alsoSee() {
        return alsoSee;
    }

    @Nonnull
    @Override
    public Set<Link> links() {
        return links;
    }
    
}
