package net.ramify.strategy.merge.event;

import net.ramify.model.event.Event;
import net.ramify.model.event.collection.HasPersonEvents;

import javax.annotation.Nonnull;
import java.util.HashSet;
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
        if (birth.isImpossibleMerge()) return Result.impossible();

        final var death = deaths.merge(e1.findDeath(), e2.findDeath());
        if (death.isImpossibleMerge()) return Result.impossible();

        final var events = new HashSet<Event>();
        events.addAll(e1.events());
        events.addAll(e2.events());
        events.removeIf(Event::isUnique);

        birth.ifPresent(events::add);
        death.ifPresent(events::add);

        return Result.of(events);

    }

    @Override
    public Set<? extends Event> just(final HasPersonEvents events) {
        return events.events();
    }

}
