package net.ramify.strategy.event.merge;

import net.ramify.model.event.Event;
import net.ramify.model.event.collection.HasPersonEvents;
import net.ramify.strategy.merge.Merger;

import javax.annotation.Nonnull;
import java.util.Set;

public class DefaultEventsMerger implements EventsMerger {

    private final BirthMerger births;
    private final DeathMerger deaths;

    public DefaultEventsMerger(final BirthMerger births, final DeathMerger deaths) {
        this.births = births;
        this.deaths = deaths;
    }

    @Nonnull
    @Override
    public Result<Set<? extends Event>> merge(final HasPersonEvents e1, final HasPersonEvents e2) {

        final var birth = births.merge(e1.findBirth(), e2.findBirth());
        if (birth.isImpossible()) return Merger.impossible();

        final var death = deaths.merge(e1.findDeath(), e2.findDeath());
        if (death.isImpossible()) return Merger.impossible();

        throw new UnsupportedOperationException(); //TODO

    }

    @Override
    public Set<? extends Event> just(final HasPersonEvents events) {
        return events.events();
    }

}
