package net.ramify.model.event.merge;

import net.ramify.model.event.Event;
import net.ramify.model.strategy.Merger;

public interface EventMerger<E extends Event> extends Merger<E, E> {

    @Override
    default E just(final E event) {
        return event;
    }

}
