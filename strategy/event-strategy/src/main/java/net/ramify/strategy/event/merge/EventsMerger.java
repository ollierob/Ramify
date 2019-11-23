package net.ramify.strategy.event.merge;

import net.ramify.model.event.Event;
import net.ramify.model.event.collection.HasPersonEvents;
import net.ramify.strategy.merge.Merger;

import javax.annotation.Nonnull;
import java.util.Set;

public class EventsMerger implements Merger<HasPersonEvents, Set<? extends Event>> {

    private final BirthMerger births;

    public EventsMerger(final BirthMerger births) {
        this.births = births;
    }

    @Nonnull
    @Override
    public Result<Set<? extends Event>> merge(final HasPersonEvents e1, final HasPersonEvents e2) {

        final var bm = births.merge(e1.findBirth(), e2.findBirth());
        if (bm.isImpossible()) return Merger.impossible();

        throw new UnsupportedOperationException(); //TODO

    }

    @Override
    public Set<? extends Event> just(final HasPersonEvents events) {
        return events.events();
    }

}
