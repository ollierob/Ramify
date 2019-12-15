package net.ramify.strategy.merge.event;

import net.ramify.model.event.Event;
import net.ramify.model.event.collection.HasPersonEvents;
import net.ramify.model.event.collection.MutablePersonEventSet;
import net.ramify.model.event.collection.PersonEventSet;

import javax.annotation.Nonnull;

public class DefaultEventsMerger implements EventsMerger {

    private final BirthMerger births;
    private final DeathMerger deaths;

    public DefaultEventsMerger(final BirthMerger births, final DeathMerger deaths) {
        this.births = births;
        this.deaths = deaths;
    }

    @Nonnull
    @Override
    public Result<PersonEventSet> merge(final HasPersonEvents e1, final HasPersonEvents e2) {

        final var birth = births.merge(e1.events().findBirth(), e2.events().findBirth());
        if (birth.isImpossibleMerge()) return Result.impossible();

        final var death = deaths.merge(e1.events().findDeath(), e2.events().findDeath());
        if (death.isImpossibleMerge()) return Result.impossible();

        final var events = new MutablePersonEventSet();
        events.addAll(e1.events());
        events.addAll(e2.events());
        events.removeIf(Event::isUnique);

        birth.ifPresent(events::add);
        death.ifPresent(events::add);

        return Result.of(events);

    }

    @Override
    public PersonEventSet just(final HasPersonEvents events) {
        return events.events();
    }

}
