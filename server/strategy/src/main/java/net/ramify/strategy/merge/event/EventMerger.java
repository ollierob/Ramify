package net.ramify.strategy.merge.event;

import net.ramify.model.event.Event;
import net.ramify.strategy.merge.Merger;

public interface EventMerger<E extends Event> extends Merger<E, E> {

    @Override
    default E just(final E event) {
        return event;
    }

}
