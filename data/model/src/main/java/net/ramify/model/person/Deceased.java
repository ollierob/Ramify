package net.ramify.model.person;

import net.ramify.model.date.DateRange;
import net.ramify.model.event.Death;
import net.ramify.model.event.PersonalEvents;

import javax.annotation.Nonnull;

public class Deceased extends DelegatedPersonalAttributes {

    public Deceased(final PersonalAttributes delegate) {
        super(delegate);
    }

    @Nonnull
    @Override
    public PersonalEvents inferredEvents() {
        return super.inferredEvents().and(new Death(DateRange.before(this.date())));
    }

}
