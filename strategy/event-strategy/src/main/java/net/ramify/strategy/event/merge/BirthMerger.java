package net.ramify.strategy.event.merge;

import net.ramify.model.date.DateRange;
import net.ramify.model.event.type.Birth;
import net.ramify.model.event.type.birth.GenericBirth;
import net.ramify.model.person.PersonId;

public class BirthMerger extends AbstractEventMerger<Birth> {

    @Override
    Birth merge(PersonId id, DateRange date) {
        return new GenericBirth(id, date);
    }

}
