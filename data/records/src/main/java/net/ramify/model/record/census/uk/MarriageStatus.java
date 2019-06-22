package net.ramify.model.record.census.uk;

import net.ramify.model.date.DateRange;
import net.ramify.model.event.Event;
import net.ramify.model.event.type.civil.GenericMarriage;
import net.ramify.model.person.PersonId;
import net.ramify.model.record.census.CensusRecord;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.Set;

public interface MarriageStatus {

    @Nonnull
    Set<Event> inferEvents(PersonId personId, CensusRecord census);

    MarriageStatus UNMARRIED = (id, census) -> Collections.emptySet();
    MarriageStatus NONE = UNMARRIED;
    MarriageStatus WIDOWED = (id, census) -> Collections.singleton(new GenericMarriage(id, DateRange.before(census.date())));

}
