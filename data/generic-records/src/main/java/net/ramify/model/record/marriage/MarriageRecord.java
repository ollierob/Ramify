package net.ramify.model.record.marriage;

import net.ramify.model.person.event.Marriage;
import net.ramify.model.record.Record;

import javax.annotation.Nonnull;

public interface MarriageRecord extends Record {

    @Nonnull
    Marriage marriage();

}
