package net.ramify.model.event.type.civil;

import net.ramify.model.date.DateRange;
import net.ramify.model.event.type.AbstractEvent;
import net.ramify.model.event.type.Death;
import net.ramify.model.person.PersonId;

public class GenericDeath extends AbstractEvent<GenericDeath> implements Death {

    public GenericDeath(final PersonId personId, final DateRange date) {
        super(personId, date);
    }

}
