package net.ramify.model.person.event;

import net.ramify.model.event.DateRange;

public class Will extends AbstractLogicalEvent {

    public Will(final DateRange date) {
        super(date, "Will");
    }

}
