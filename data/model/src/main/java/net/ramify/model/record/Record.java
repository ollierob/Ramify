package net.ramify.model.record;

import net.ramify.model.event.Events;
import net.ramify.model.family.Family;

import javax.annotation.Nonnull;

public interface Record {

    @Nonnull
    Events events();

    @Nonnull
    Family family();

}
