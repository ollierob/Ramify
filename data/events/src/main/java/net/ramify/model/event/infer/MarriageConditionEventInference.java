package net.ramify.model.event.infer;

import net.ramify.model.date.BeforeDate;
import net.ramify.model.event.Event;
import net.ramify.model.event.type.civil.GenericMarriage;
import net.ramify.model.person.PersonId;
import net.ramify.model.record.Record;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.Set;

public interface MarriageConditionEventInference {

    @Nonnull
    Set<Event> inferEvents(PersonId personId, Record record);

    MarriageConditionEventInference UNMARRIED = (id, record) -> Collections.emptySet();
    MarriageConditionEventInference MARRIED = (id, record) -> Collections.singleton(new GenericMarriage(id, BeforeDate.strictlyBefore(record.date())));
    MarriageConditionEventInference WIDOWED = MARRIED;

}
