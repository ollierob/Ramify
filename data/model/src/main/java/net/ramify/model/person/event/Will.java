package net.ramify.model.person.event;

import net.ramify.model.date.DateRange;

public class Will extends AbstractLogicalEvent {

    public Will(final DateRange date) {
        super(date, "Will");
    }

}
