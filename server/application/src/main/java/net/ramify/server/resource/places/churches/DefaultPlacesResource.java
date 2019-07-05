package net.ramify.server.resource.places.churches;

import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceDescription;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.building.Church;
import net.ramify.model.place.collection.Places;
import net.ramify.model.place.position.Position;
import net.ramify.model.place.position.PositionProvider;
import net.ramify.model.place.proto.PlaceProto;
import net.ramify.model.place.provider.PlaceDescriptionProvider;
import net.ramify.model.place.provider.PlaceProvider;
import net.ramify.model.place.region.Chapelry;
import net.ramify.model.place.region.Country;
import net.ramify.model.place.region.County;
import net.ramify.model.place.region.Parish;
import net.ramify.model.place.region.Township;
import net.ramify.model.place.region.manor.Manor;
import net.ramify.model.place.settlement.Town;
import net.ramify.model.place.settlement.Village;
import net.ramify.server.resource.places.ChurchesResource;
import net.ramify.server.resource.places.PlacesResource;
import net.ramify.utils.objects.Consumers;

import javax.annotation.CheckForNull;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.function.Predicate;

@Singleton
public class DefaultPlacesResource implements PlacesResource {

    private final PlaceProvider placeProvider;
    private final PositionProvider positionProvider;
    private final PlaceDescriptionProvider descriptionProvider;
    private final ChurchesResource churchesResource;

    @Inject
    DefaultPlacesResource(
            final PlaceProvider placeProvider,
            final PositionProvider positionProvider,
            final PlaceDescriptionProvider descriptionProvider,
            final ChurchesResource churchesResource) {
        this.placeProvider = placeProvider;
        this.positionProvider = positionProvider;
        this.descriptionProvider = descriptionProvider;
        this.churchesResource = churchesResource;
    }

    @Override
    public Place at(final PlaceId id) {
        return placeProvider.get(id);
    }

    @Override
    public Places within(final PlaceId id, final Integer depth) {
        final var place = placeProvider.get(id);
        if (place == null) return null;
        return Places.of(placeProvider.children(id, this.maxDepth(place), this.children(place)), false);
    }

    private int maxDepth(final Place place) {
        switch (place.protoType()) {
            case COUNTRY:
            case COUNTY:
                return 1;
            default:
                return 5;
        }
    }

    private Predicate<Place> children(final Place place) {
        switch (place.protoType()) {
            case COUNTRY:
                return p -> p.is(County.class);
            case COUNTY:
                return p -> p.is(Parish.class) || p.is(Manor.class);
            case PARISH:
            case CHAPELRY:
                return p -> p.is(Chapelry.class) || p.is(Township.class) || p.is(Town.class) || p.is(Village.class) || p.is(Church.class);
            default:
                return p -> true;
        }
    }

    @Override
    public Position position(final PlaceId id) {
        return positionProvider.get(id);
    }

    @CheckForNull
    @Override
    public String describe(final PlaceId id) {
        return descriptionProvider.maybeGet(id).map(PlaceDescription::description).orElse(null);
    }

    @Override
    public String describeType(final PlaceId id) {
        final var place = this.at(id);
        if (place == null) return null;
        final var country = place.find(Country.class).orElse(null);
        if (country == null) return null;
        return PlaceTypeDescriptions.describe(place.protoType(), country);
    }

    @Override
    public PlaceProto.PlaceBundle bundle(final PlaceId id) {
        final var place = this.at(id);
        if (place == null) return null;
        final var builder = PlaceProto.PlaceBundle.newBuilder().setPlace(place.toProto());
        Consumers.ifNonNull(this.position(id), pos -> builder.setPosition(pos.toProto()));
        descriptionProvider.maybeGet(id).ifPresent(d -> builder.setDescription(d.toProto()));
        Consumers.ifNonNull(this.describeType(id), builder::setTypeDescription);
        return builder.build();
    }

    @Override
    public ChurchesResource churches() {
        return churchesResource;
    }

}
