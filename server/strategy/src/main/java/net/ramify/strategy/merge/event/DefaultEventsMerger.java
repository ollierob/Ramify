package net.ramify.strategy.merge.event;

import net.ramify.model.event.PersonEvent;
import net.ramify.model.event.collection.HasPersonEvents;
import net.ramify.model.event.collection.MutablePersonEventSet;
import net.ramify.model.event.collection.PersonEventSet;
import net.ramify.model.event.merge.EventsMerger;

import javax.annotation.Nonnull;

public class DefaultEventsMerger implements EventsMerger {

    private final BirthEventMerger births;
    private final DeathEventMerger deaths;

    public DefaultEventsMerger(final BirthEventMerger births, final DeathEventMerger deaths) {
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

        final var events = MutablePersonEventSet.notPermittingMerge();
        events.addAll(e1.events());
        events.addAll(e2.events());
        events.removeIf(PersonEvent::isUnique);

        birth.ifPresent(events::add);
        death.ifPresent(events::add);

        return Result.of(events);

    }

    @Override
    public PersonEventSet just(final HasPersonEvents events) {
        return events.events();
    }

}
