package net.ramify.server.strategy;

import com.google.inject.Exposed;
import com.google.inject.PrivateModule;
import com.google.inject.Provides;
import net.ramify.model.event.merge.EventMerger;
import net.ramify.model.event.merge.UniqueEventMerger;
import net.ramify.model.event.type.BirthEvent;
import net.ramify.model.event.type.DeathEvent;
import net.ramify.model.place.merge.PlaceMerger;
import net.ramify.strategy.merge.event.BirthEventMerger;
import net.ramify.strategy.merge.event.DeathEventMerger;

import javax.annotation.Nonnull;
import javax.inject.Singleton;

class EventStrategyModule extends PrivateModule {

    @Override
    protected void configure() {
    }

    @Provides
    @Singleton
    EventMerger<BirthEvent> provideBirthMerger(final PlaceMerger placeMerger) {
        return new BirthEventMerger(placeMerger);
    }

    @Provides
    @Singleton
    EventMerger<DeathEvent> provideDeathMerger(final PlaceMerger placeMerger) {
        return new DeathEventMerger(placeMerger);
    }

    @Provides
    @Exposed
    @Singleton
    UniqueEventMerger provideEventMergers(final EventMerger<BirthEvent> births, final EventMerger<DeathEvent> deaths) {
        return new UniqueEventMerger() {

            @Nonnull
            @Override
            public EventMerger<BirthEvent> births() {
                return births;
            }

            @Nonnull
            @Override
            public EventMerger<DeathEvent> deaths() {
                return deaths;
            }

        };
    }

}
