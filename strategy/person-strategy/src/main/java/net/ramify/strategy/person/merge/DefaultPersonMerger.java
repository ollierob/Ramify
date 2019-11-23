package net.ramify.strategy.person.merge;

import net.ramify.model.person.Person;
import net.ramify.strategy.event.merge.EventsMerger;
import net.ramify.strategy.merge.Merger;
import net.ramify.strategy.person.merge.gender.GenderMerger;
import net.ramify.strategy.person.merge.name.NameMerger;

import javax.annotation.Nonnull;

public class DefaultPersonMerger implements PersonMerger {

    private final NameMerger nameMerger;
    private final GenderMerger genderMerger;
    private final EventsMerger eventsMerger;

    public DefaultPersonMerger(
            final NameMerger nameMerger,
            final GenderMerger genderMerger,
            final EventsMerger eventsMerger) {
        this.nameMerger = nameMerger;
        this.genderMerger = genderMerger;
        this.eventsMerger = eventsMerger;
    }

    @Nonnull
    @Override
    public Result<Person> merge(final Person p1, final Person p2) {

        final var name = nameMerger.merge(p1.name(), p2.name());
        if (name.isImpossible()) return Merger.impossible();

        final var gender = genderMerger.merge(p1.gender(), p2.gender());
        if (gender.isImpossible()) return Merger.impossible();

        final var events = eventsMerger.merge(p1, p2);
        if (events.isImpossible()) return Merger.impossible();

        throw new UnsupportedOperationException(); //TODO

    }

}
