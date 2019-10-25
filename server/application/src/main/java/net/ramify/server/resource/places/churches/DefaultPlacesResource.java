package net.ramify.server.resource.places.churches;

import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceDescription;
import net.ramify.model.place.PlaceGroup;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.collection.Places;
import net.ramify.model.place.position.Position;
import net.ramify.model.place.position.PositionProvider;
import net.ramify.model.place.proto.PlaceProto;
import net.ramify.model.place.provider.PlaceDescriptionProvider;
import net.ramify.model.place.provider.PlaceGroupProvider;
import net.ramify.model.place.provider.PlaceProvider;
import net.ramify.model.place.region.Country;
import net.ramify.server.resource.places.ChurchesResource;
import net.ramify.server.resource.places.PlaceFavouritesResource;
import net.ramify.server.resource.places.PlacesResource;
import net.ramify.utils.objects.Consumers;

import javax.annotation.CheckForNull;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DefaultPlacesResource implements PlacesResource {

    private final PlaceProvider placeProvider;
    private final PlaceGroupProvider placeGroupProvider;
    private final PositionProvider positionProvider;
    private final PlaceDescriptionProvider descriptionProvider;
    private final ChurchesResource churchesResource;
    private final PlaceFavouritesResource favouritesResource;

    @Inject
    DefaultPlacesResource(
            final PlaceProvider placeProvider,
            final PlaceGroupProvider placeGroupProvider,
            final PositionProvider positionProvider,
            final PlaceDescriptionProvider descriptionProvider,
            final ChurchesResource churchesResource,
            final PlaceFavouritesResource favouritesResource) {
        this.placeProvider = placeProvider;
        this.placeGroupProvider = placeGroupProvider;
        this.positionProvider = positionProvider;
        this.descriptionProvider = descriptionProvider;
        this.churchesResource = churchesResource;
        this.favouritesResource = favouritesResource;
    }

    @CheckForNull
    @Override
    public PlaceGroup group(final PlaceGroupId id) {
        return placeGroupProvider.get(id);
    }

    @Override
    public Place at(final PlaceId id) {
        return placeProvider.get(id);
    }

    @Override
    public Places within(final PlaceId id, final Integer depth) {
        final var place = placeProvider.get(id);
        if (place == null) return null;
        final var childTypes = place.childTypes();
        final var children = placeProvider.children(id, this.maxDepth(place), childTypes.isEmpty() ? p -> true : p -> p.isAny(childTypes));
        return Places.of(children, false);
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

    @Override
    public Places countries() {
        return Places.of(placeProvider.countries(), false);
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
        this.within(id, null).forEach(child -> builder.addChild(child.toProto(false)));
        return builder.build();
    }

    @Override
    public ChurchesResource churches() {
        return churchesResource;
    }

    @Override
    public PlaceFavouritesResource favourites() {
        return favouritesResource;
    }

}
