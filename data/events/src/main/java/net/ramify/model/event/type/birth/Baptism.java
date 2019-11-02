package net.ramify.model.event.type.birth;

import net.ramify.model.event.type.LifeEvent;

import javax.annotation.Nonnull;

public interface Baptism extends LifeEvent {

    @Nonnull
    @Override
    default String title() {
        return "Baptism";
    }

}
