package net.ramify.server.strategy;

import com.google.inject.Exposed;
import com.google.inject.PrivateModule;
import com.google.inject.Provides;
import net.ramify.model.place.merge.PlaceMerger;
import net.ramify.strategy.merge.place.ChildPlaceMerger;

import javax.inject.Singleton;

class PlaceStrategyModule extends PrivateModule {

    @Override
    protected void configure() {
    }

    @Provides
    @Exposed
    @Singleton
    PlaceMerger providePlaceMerger() {
        return new ChildPlaceMerger(PlaceMerger.NO_MERGE);
    }

}
