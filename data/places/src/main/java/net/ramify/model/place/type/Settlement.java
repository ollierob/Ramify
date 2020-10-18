package net.ramify.model.place.type;

public interface Settlement extends SettlementOrRegion {

    @Override
    default <R> R handleWith(final PlaceHandler<R> handler) {
        return handler.handle(this);
    }

}
