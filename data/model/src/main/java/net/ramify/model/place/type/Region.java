package net.ramify.model.place.type;

public interface Region extends SettlementOrRegion {

    @Override
    Region parent();

    @Override
    default <R> R handleWith(final PlaceHandler<R> handler) {
        return handler.handle(this);
    }

}
