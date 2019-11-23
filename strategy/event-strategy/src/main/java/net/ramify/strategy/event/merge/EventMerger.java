package net.ramify.strategy.event.merge;

import net.ramify.model.event.Event;
import net.ramify.strategy.merge.Merger;

public interface EventMerger<E extends Event> extends Merger<E, E> {

    @Override
    default E just(final E event) {
        return event;
    }

}
