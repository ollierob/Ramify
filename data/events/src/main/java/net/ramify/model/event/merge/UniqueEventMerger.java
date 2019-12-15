package net.ramify.model.event.merge;

import net.ramify.model.event.type.BirthEvent;
import net.ramify.model.event.type.DeathEvent;
import net.ramify.model.strategy.Merger;

import javax.annotation.Nonnull;

public interface UniqueEventMerger {

    @Nonnull
    EventMerger<BirthEvent> births();

    @Nonnull
    default Merger.Result<BirthEvent> merge(final BirthEvent b1, final BirthEvent b2) {
        return this.births().merge(b1, b2);
    }

    @Nonnull
    EventMerger<DeathEvent> deaths();

    @Nonnull
    default Merger.Result<DeathEvent> merge(final DeathEvent d1, final DeathEvent d2) {
        return this.deaths().merge(d1, d2);
    }

}
