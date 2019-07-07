package net.ramify.model.place.type;

public interface Region extends SettlementOrRegion {

    @Deprecated
    @Override
    default Region region() {
        return this;
    }

    @Override
    Region parent();

    @Override
    default <R> R handleWith(final PlaceHandler<R> handler) {
        return handler.handle(this);
    }

}
