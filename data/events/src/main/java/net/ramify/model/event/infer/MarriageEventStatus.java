package net.ramify.model.event.infer;

import net.ramify.model.date.DateRange;
import net.ramify.model.event.Event;
import net.ramify.model.event.type.civil.GenericMarriage;
import net.ramify.model.person.PersonId;
import net.ramify.model.record.Record;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.Set;

public interface MarriageEventStatus {

    @Nonnull
    Set<Event> inferEvents(PersonId personId, Record record);

    MarriageEventStatus UNMARRIED = (id, record) -> Collections.emptySet();
    MarriageEventStatus NONE = UNMARRIED;
    MarriageEventStatus MARRIED = (id, record) -> Collections.singleton(new GenericMarriage(id, DateRange.before(record.date())));
    MarriageEventStatus WIDOWED = MARRIED;

}
