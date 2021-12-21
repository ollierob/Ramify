package net.ramify.model.event.merge;

import net.ramify.model.event.type.BirthEvent;
import net.ramify.model.event.type.DeathEvent;
import net.ramify.model.strategy.MergeResult;

import javax.annotation.Nonnull;

public interface UniqueEventMerger {

    @Nonnull
    EventMerger<BirthEvent> births();

    @Nonnull
    default MergeResult<BirthEvent> merge(final BirthEvent b1, final BirthEvent b2) {
        return this.births().merge(b1, b2);
    }

    @Nonnull
    EventMerger<DeathEvent> deaths();

    @Nonnull
    default MergeResult<DeathEvent> merge(final DeathEvent d1, final DeathEvent d2) {
        return this.deaths().merge(d1, d2);
    }

    UniqueEventMerger NOT_ALLOWED = new UniqueEventMerger() {

        private final EventMerger<BirthEvent> birthMerger = EventMerger.notPermitted();
        private final EventMerger<DeathEvent> deathMerger = EventMerger.notPermitted();

        @Nonnull
        @Override
        public EventMerger<BirthEvent> births() {
            return birthMerger;
        }

        @Nonnull
        @Override
        public EventMerger<DeathEvent> deaths() {
            return deathMerger;
        }

    };

}
