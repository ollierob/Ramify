package net.ramify.model.person.event;

import net.ramify.model.event.DateRange;

public class Occupation extends AbstractLogicalEvent {

    public Occupation(
            final DateRange date,
            final String description) {
        super(date, description);
    }

}
