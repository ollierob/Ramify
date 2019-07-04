package net.ramify.server.resource.places.churches;

import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.collection.Places;
import net.ramify.model.place.position.Position;
import net.ramify.model.place.position.PositionProvider;
import net.ramify.model.place.proto.PlaceProto;
import net.ramify.model.place.provider.PlaceProvider;
import net.ramify.model.place.region.Country;
import net.ramify.server.resource.places.ChurchesResource;
import net.ramify.server.resource.places.PlacesResource;
import net.ramify.utils.objects.Consumers;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DefaultPlacesResource implements PlacesResource {

    private final PlaceProvider placeProvider;
    private final PositionProvider positionProvider;
    private final ChurchesResource churchesResource;

    @Inject
    DefaultPlacesResource(
            final PlaceProvider placeProvider,
            final PositionProvider positionProvider,
            final ChurchesResource churchesResource) {
        this.placeProvider = placeProvider;
        this.positionProvider = positionProvider;
        this.churchesResource = churchesResource;
    }

    @Override
    public Place at(final PlaceId id) {
        return placeProvider.get(id);
    }

    @Override
    public Places within(final PlaceId id, final int depth) {
        return Places.of(placeProvider.children(id, depth), false);
    }

    @Override
    public Position position(final PlaceId id) {
        return positionProvider.get(id);
    }

    @Override
    public String describe(final PlaceId id) {
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
        Consumers.ifNonNull(this.describe(id), builder::setDescription);
        return builder.build();
    }

    @Override
    public ChurchesResource churches() {
        return churchesResource;
    }

}
