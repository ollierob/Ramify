package net.ramify.model.event.type.church;

import net.ramify.model.date.DateRange;
import net.ramify.model.event.AbstractEvent;
import net.ramify.model.event.type.Burial;
import net.ramify.model.person.PersonId;

public class GenericChurchBurial extends AbstractEvent<GenericChurchBurial> implements Burial {

    public GenericChurchBurial(final PersonId personId, final DateRange date) {
        super(personId, date);
    }

}
