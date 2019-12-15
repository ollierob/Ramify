package net.ramify.model.event.type.will;

import net.ramify.model.event.type.PostDeathEvent;
import net.ramify.model.event.type.UniqueEvent;

import javax.annotation.Nonnull;

public interface ProbateEvent extends UniqueEvent, PostDeathEvent {

    @Override
    default String title() {
        return "Probate";
    }

    @Nonnull
    @Override
    default Class<? extends UniqueEvent> uniqueType() {
        return ProbateEvent.class;
    }

}
