package net.ramify.strategy.merge.person;

import net.ramify.model.person.Person;
import net.ramify.model.record.GenericRecordPerson;
import net.ramify.model.event.merge.EventsMerger;
import net.ramify.model.strategy.MergeResult;
import net.ramify.strategy.merge.person.gender.GenderMerger;
import net.ramify.strategy.merge.person.name.PersonNamesMerger;

import javax.annotation.Nonnull;

public class DefaultPersonMerger implements PersonMerger {

    private final PersonNamesMerger nameMerger;
    private final GenderMerger genderMerger;
    private final EventsMerger eventsMerger;

    public DefaultPersonMerger(
            final PersonNamesMerger nameMerger,
            final GenderMerger genderMerger,
            final EventsMerger eventsMerger) {
        this.nameMerger = nameMerger;
        this.genderMerger = genderMerger;
        this.eventsMerger = eventsMerger;
    }

    @Nonnull
    @Override
    public MergeResult<Person> merge(final Person p1, final Person p2) {

        final var gender = genderMerger.merge(p1.gender(), p2.gender());
        if (!gender.isMergeable()) return gender.cannotMerge();

        final var name = nameMerger.merge(p1, p2);
        if (!name.isMergeable()) return name.cannotMerge();

        final var events = eventsMerger.merge(p1, p2);
        if (!events.isMergeable()) return events.cannotMerge();

        final var person = new GenericRecordPerson(p1.personId(), name.require(), gender.require(), events.require(), null);
        return MergeResult.of(person);

    }

}
