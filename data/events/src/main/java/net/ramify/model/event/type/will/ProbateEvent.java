package net.ramify.model.event.type.will;

import net.ramify.model.event.type.PostDeathEvent;
import net.ramify.model.event.type.UniqueEvent;

public interface ProbateEvent extends UniqueEvent, PostDeathEvent {

    @Override
    default String title() {
        return "Probate";
    }

}
