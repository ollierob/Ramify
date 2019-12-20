package net.ramify.server.resource.places;

import net.meerkat.functions.consumers.Consumers;
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
import net.ramify.utils.collections.ListUtils;

import javax.annotation.CheckForNull;
import javax.inject.Inject;
import javax.inject.Singleton;

import static net.ramify.utils.StringUtils.isBlank;

@Singleton
public class DefaultPlacesResource implements PlacesResource {

    private final PlaceProvider placeProvider;
    private final PlaceGroupProvider placeGroupProvider;
    private final PositionProvider positionProvider;
    private final PlaceDescriptionProvider descriptionProvider;
    private final PlaceFavouritesResource favouritesResource;

    @Inject
    DefaultPlacesResource(
            final PlaceProvider placeProvider,
            final PlaceGroupProvider placeGroupProvider,
            final PositionProvider positionProvider,
            final PlaceDescriptionProvider descriptionProvider,
            final PlaceFavouritesResource favouritesResource) {
        this.placeProvider = placeProvider;
        this.placeGroupProvider = placeGroupProvider;
        this.positionProvider = positionProvider;
        this.descriptionProvider = descriptionProvider;
        this.favouritesResource = favouritesResource;
    }

    @Override
    public PlaceGroup group(final PlaceId id) {
        return placeGroupProvider.getGroup(id);
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
        if (place instanceof Country) return 1;
        return 5;
    }

    @Override
    public Places countries() {
        return Places.of(ListUtils.sortedCopy(placeProvider.countries(), Place.SORT_BY_NAME), false);
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
    public Places find(final String name, final PlaceId within, final int limit) {
        if (isBlank(name)) return Places.of();
        return Places.of(placeProvider.findByName(name, limit, within), true);
    }

    @Override
    public PlaceFavouritesResource favourites() {
        return favouritesResource;
    }

}
